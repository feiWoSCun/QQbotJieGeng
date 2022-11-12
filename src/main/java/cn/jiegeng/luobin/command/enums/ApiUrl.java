package cn.jiegeng.luobin.command.enums;

public enum ApiUrl {
    Chat("http://api.qingyunke.com/api.php?key=free&appid=0&msg=");
    private String url;

    ApiUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
