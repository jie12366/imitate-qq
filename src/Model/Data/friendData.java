package Model.Data;

/**
 * 熊义杰
 * 好友列表的数据类
 */
public class friendData {
    private String head; //好友头像
    private String account; //好友账号

    /**
     * friendData的构造函数
     * @param head
     * @param account
     */
    public friendData(String head,String account){
        this.head = head;
        this.account = account;
    }

    /**
     * friendData的set和get函数
     */
    public void setHead(String head){
        this.head = head;
    }
    public void setAccount(String account){
        this.account = account;
    }
    public String getHead(){
        return head;
    }
    public String getAccount(){
        return account;
    }
}
