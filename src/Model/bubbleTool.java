package Model;

/**
 * 消息气泡的助手方法，使气泡自适应高度和宽度
 */

public class bubbleTool {

    /**
     * 判断传入的字符是否是汉字
     * @param c 传入的字符参数
     * @return 返回是否是汉字
     */
    private static final boolean isChinese(char c){
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    /**
     * 设置气泡的宽度
     * @param Msg 传入的消息字符串
     * @return 返回气泡的宽度
     */
    public static double getWidth(String Msg){
        int len = Msg.length();
        double width = 5;    //这里的20 = 17.5px
        for(int i = 0; i < len; i++){
            if(isChinese(Msg.charAt(i))){
                width += 17;  //一个汉字设置为17大小
            }
            else {
                width += 9;   //不是汉字的设置为9大小
            }
        }
        if(width <= 300){
            return width;
        }
        else{
            return 300;
        }
    }

    /**
     * 设置气泡的高度
     * @param Msg 传入的字符串
     * @return 返回气泡的高度
     */
    public static double getHeight(String Msg){
        int len = Msg.length();
        double width = 5;    //这里的20 = 17.5px
        double height = 25;
        for(int i = 0; i < len; i++){
            if(isChinese(Msg.charAt(i))){
                width += 17;  //一个汉字设置为17大小
            }
            else {
                width += 9;   //不是汉字的设置为9大小
            }
            if(width >= 300){
                height += 20;
                width = 20;  //高度增加，宽度初始化
            }
        }
        return height;
    }
}
