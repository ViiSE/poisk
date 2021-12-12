package com.github.viise.poisk.flt;

import com.github.viise.poisk.FilterList;
import com.github.viise.poisk.sch.SchByCondition;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class FltListTestNG {

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
    public void apply() {
        FilterList<Person> fltList = new FltList<>(
                new SchByCondition<>(
                        person -> person.getNote() != null
                )
        );

        List<Person> result = fltList.apply(people);
        List<String> names = result.stream()
                .map(Person::getName)
                .collect(Collectors.toList());

        assertEquals(result.size(), 3);
        assertTrue(names.contains("John"));
        assertTrue(names.contains("Adam"));
        assertTrue(names.contains("Petr"));
    }

    @Test
    public void apply_notFound() {
        FilterList<Person> fltList = new FltList<>(
                new SchByCondition<>(
                        person -> person.getName().equals("Igor")
                )
        );
        List<Person> result = fltList.apply(people);
        assertTrue(result.isEmpty());
    }

    @Test
    public void apply_schIsNull() {
        FilterList<Person> fltList = new FltList<>(null);
        List<Person> result = fltList.apply(people);
        assertTrue(result.isEmpty());
    }
}
