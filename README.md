# Mocker
A java or android data mocker library.

# Example

data class
``` java
public class Inner {

    @NumberRange(from = 18, to = 40)
    public int age;

    @Len(3)
    @Lang(Lang.ZH_CN)
    public String name;
}

public class Data {

    public Inner inner;

    @Time
    public long time;

    @Time
    public String timeFmt;

    @Bool(true)
    public boolean show;
}

```

mock
``` java
public class Test {
    public static void main(String[] args) {
        Data data = new Data();
        data = Mocker.mock(data);

        System.out.println(data.inner.name);
    }
}

```
