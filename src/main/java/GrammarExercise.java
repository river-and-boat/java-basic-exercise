import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/*
* 需求分析
* 1. 输出的字符串需要有序且去重，sort、distinct
* 2. 将输入字符串转为数组后，判断数组中元素是否有空：isEmpty()；且是否全是字母，可用正则表达式匹配match('[a-zA-Z]+')
* 3. 比较元素时不区分大小写，可全部转换为大写或小写再进行比较：list2.replaceAll(String::toLowCase); s -> list2.contains(s.toLowerCase())
* 4. 特别注意，由于标准输出给每个字母后都加了空格，因此嵌套了一层stream()，第一层stream中每个lambda元素表示字符串，第二层每个元素表示字符，再进行join(" ")
* */

public class GrammarExercise {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        //需要从命令行读入
        String firstWordList = scanner.nextLine();
        String secondWordList = scanner.nextLine();

        List<String> result = findCommonWordsWithSpace(firstWordList,secondWordList);
        //按要求输出到命令行

    }

    public static List<String> findCommonWordsWithSpace(String firstWordList, String secondWordList) {
        //在这编写实现代码
        List<String> firstWordArrList = Arrays.asList(firstWordList.split(","));
        List<String> secondWordArrList = Arrays.asList(secondWordList.split(","));

        // 判断输入字符是否符合要求，先进行去重操作，减少后续元素操作量
        List<String> firstWordMidList = findAnswer(firstWordArrList);

        List<String> secondWordMidList = findAnswer(secondWordArrList);

        if((firstWordMidList.size() != firstWordArrList.size() ||
                secondWordMidList.size() != secondWordArrList.size())) {
            throw new RuntimeException("input not valid");
        }

        List<String> result = firstWordMidList.stream()
                .filter(s -> secondWordMidList
                        .contains(s))
                .sorted()// 先排序再去重?
                .distinct()
                .collect(Collectors.toList());

        return result;
    }

    // 将重复代码进行提取
    private static List<String> findAnswer(List<String> input) {
        return input.stream()
                .filter(s -> !s.isEmpty() && s.matches("[a-zA-Z]+")) //先排除异常情况
                .map(String::toUpperCase)
                .map(s -> {
                    String[] m = s.split("");// 因为前面已经对异常情况做了排除，这里可以直接用
                    return Arrays
                            .stream(m)
                            .collect(Collectors.joining(" "));
                })
                .collect(Collectors.toList());
    }
}
