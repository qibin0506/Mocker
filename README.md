# Mocker
A java or android data mock library.

# Getting Started
first, define your data class
``` java
public class Data {
    @Len(5)
    @Lang(Lang.EN_US_LOWER)
    private String str;
}
```

then, mock it!
``` java
Data data = new Data();
data = Mocker.mock(data);
System.out.println(data.str);
// output: uoxyt
```

# Annotations
We provided 5 annotations to help you custom data style. See [Bool](https://github.com/qibin0506/Mocker/blob/master/mocker/src/main/java/org/loader/mocker/annotation/Bool.java), [Lang](https://github.com/qibin0506/Mocker/blob/master/mocker/src/main/java/org/loader/mocker/annotation/Lang.java), [Len](https://github.com/qibin0506/Mocker/blob/master/mocker/src/main/java/org/loader/mocker/annotation/Len.java), [NumberRange](https://github.com/qibin0506/Mocker/blob/master/mocker/src/main/java/org/loader/mocker/annotation/NumberRange.java), [Time](https://github.com/qibin0506/Mocker/blob/master/mocker/src/main/java/org/loader/mocker/annotation/Time.java) for detail.


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
    
    @Len(5)
    public List<String> friends;
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
