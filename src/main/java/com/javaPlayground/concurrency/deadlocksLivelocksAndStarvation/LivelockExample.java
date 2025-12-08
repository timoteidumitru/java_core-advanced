package com.javaPlayground.concurrency.deadlocksLivelocksAndStarvation;

public class LivelockExample {

    static class Spoon {
        private Person owner;

        public Spoon(Person p) { this.owner = p; }

        public Person getOwner() { return owner; }
        public void setOwner(Person p) { owner = p; }
    }

    static class Person {
        private final String name;
        private boolean hungry = true;

        public Person(String name) {
            this.name = name;
        }

        public void eatWith(Spoon spoon, Person partner) {
            while (hungry) {
                if (spoon.getOwner() != this) {
                    sleep(10);
                    continue;
                }

                if (partner.hungry) {
                    System.out.println(name + ": You eat first, buddy!");
                    spoon.setOwner(partner);
                    continue;
                }

                System.out.println(name + " is eating!");
                hungry = false;
                spoon.setOwner(partner);
            }
        }
    }

    static void sleep(long ms) { try { Thread.sleep(ms); } catch (Exception ignored) {} }

    public static void main(String[] args) {
        Person p1 = new Person("Alice");
        Person p2 = new Person("Bob");
        Spoon spoon = new Spoon(p1);

        new Thread(() -> p1.eatWith(spoon, p2)).start();
        new Thread(() -> p2.eatWith(spoon, p1)).start();
    }
}

