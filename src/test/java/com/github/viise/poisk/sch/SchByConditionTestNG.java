package com.github.viise.poisk.sch;

import com.github.viise.poisk.SearchList;
import com.github.viise.poisk.NotFoundException;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class SchByConditionTestNG {

    @SuppressWarnings("InnerClassMayBeStatic")
    private class Person {
        private final String name;
        private final String note;

        public Person(String name, String note) {
            this.name = name;
            this.note = note;
        }

        public String getName() {
            return name;
        }

        public String getNote() {
            return note;
        }
    }

    private final List<Person> people = new ArrayList<>();

    @BeforeClass
    public void setUp() {
        people.add(new Person("John", "Hello world!"));
        people.add(new Person("Eva", null));
        people.add(new Person("Adam", "I love cookie"));
        people.add(new Person("Petr", "I am the number one!"));
        people.add(new Person("Vladimir", null));
    }

    @Test
    public void find() throws NotFoundException {
        SearchList<Person> schCond = new SchByCondition<>(
                person -> person.getNote() != null
        );
        List<Person> result = schCond.find(people);
        List<String> names = result.stream()
                .map(Person::getName)
                .collect(Collectors.toList());

        assertEquals(result.size(), 3);
        assertTrue(names.contains("John"));
        assertTrue(names.contains("Adam"));
        assertTrue(names.contains("Petr"));
    }

    @Test
    public void find_oneItemIsNull() throws NotFoundException {
        List<Integer> items = new ArrayList<Integer>() {{
            add(1);
            add(null);
            add(5);
        }};

        SearchList<Integer> schCond = new SchByCondition<>(
                item -> item > 1
        );
        List<Integer> result = schCond.find(items);

        assertEquals(result.size(), 1);
        assertTrue(items.contains(1));
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_notFound() throws NotFoundException {
        new SchByCondition<Person>(
                person -> person.getName().equals("Igor")
        ).find(people);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsEmpty() throws NotFoundException {
        new SchByCondition<>(Objects::nonNull).find(new ArrayList<>());
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_itemsIsNull() throws NotFoundException {
        new SchByCondition<>(Objects::nonNull).find(null);
    }

    @Test(expectedExceptions = NotFoundException.class)
    public void find_conditionIsNull() throws NotFoundException {
        new SchByCondition<Person>(null).find(people);
    }
}
