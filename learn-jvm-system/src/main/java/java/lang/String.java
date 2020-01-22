package java.lang;

/**
 * @author xrb
 * @create 2020-01-22 22:22
 */
public class String {
    static {
        System.out.println("自定义String的静态代码块");
    }

    //错误: 在类 java.lang.String 中找不到 main 方法, 请将 main 方法定义为:
    //   public static void main(String[] args)
    //否则 JavaFX 应用程序类必须扩展javafx.application.Application
    public static void main(String[] args) {
        System.out.println();
    }
}
