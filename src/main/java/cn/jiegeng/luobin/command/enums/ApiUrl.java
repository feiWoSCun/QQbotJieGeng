package cn.jiegeng.luobin.command.enums;

public enum ApiUrl {
    Chat("http://api.qingyunke.com/api.php?key=free&appid=0&msg="),
    Wheather("https://api.seniverse.com/v3/weather/now.json?key=SVgWU-Ou6p9TbZxUH&location=Yibin&language=zh-Hans&unit=c");
    private String url;

    ApiUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
