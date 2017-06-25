package com.dmurchkov.parser;

public class WordCount {
    private String value;
    private Integer count;

    public WordCount(String value, Integer count) {
        this.value = value;
        this.count = count;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        WordCount wordCount = (WordCount) o;

        if (value != null ? !value.equals(wordCount.value) : wordCount.value != null) return false;
        return count != null ? count.equals(wordCount.count) : wordCount.count == null;

    }

    @Override
    public int hashCode() {
        int result = value != null ? value.hashCode() : 0;
        result = 31 * result + (count != null ? count.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "WordCount{" +
                "value='" + value + '\'' +
                ", count=" + count +
                '}';
    }
}
