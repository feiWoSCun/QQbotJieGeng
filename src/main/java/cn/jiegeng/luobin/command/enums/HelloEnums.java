package cn.jiegeng.luobin.command.enums;

public enum HelloEnums {
    MORNING("早上好"),
    AFTERNOON("中午好"),
    EVENING("晚上好"),
    SORRY("原谅桔梗现在还不是很聪明o_o ....\n"),
    DOHELP("原为阁下效劳"),
    NOPRIVILEGES("对不起,您没有相关权限(oﾟvﾟ)ノ"),
    DONTKNOW("桔梗不知道发生什么事了,添加失败＞﹏＜"),
    ADDSUCCESS("添加成功!您可以告诉我这个密钥来获取值哦!\n"),
    GETWRONG("桔梗不知道发生什么事了,获取失败＞﹏＜");
    private String sayHello;

    HelloEnums(String sayHello) {
        this.sayHello = sayHello;
    }

    public String getSayHello() {
        return sayHello;
    }
}
