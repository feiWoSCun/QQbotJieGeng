package cn.jiegeng.luobin.command.enums;/**
 * 舞台再大，不上去永远是观众，静下心沉住气慢慢来
 *
 * @email: 2825097536@qq.com
 * @author: feiWoSCun
 * @creat: 2022/12/17
 */

/**
 *舞台再大，不上去永远是观众，静下心沉住气慢慢来
 *@email: 2825097536@qq.com
 *@author: feiWoSCun
 @creat: 2022/12/17
 */
public enum WeatherEnums {
TQ1(0,"晴（国内城市白天晴）"	),
TQ2(1	,"晴（国内城市夜晚晴）"	),
TQ3(2	,"晴（国外城市白天晴）"	),
TQ4(3	,"晴（国外城市夜晚晴）"	),
TQ5(4	,"多云"	),
TQ6(5	,"晴间多云"	 ),
TQ7(6	,"晴间多云"	 ),
TQ8(7	,"大部多云" ),
TQ9(8	,"大部多云"	 ),
TQ10(9	,"阴"	),
TQ11(10	,"阵雨"	),
TQ12(11	,"雷阵雨"	),
TQ13(12	,"雷阵雨伴有冰雹"	  ),
TQ14(13	,"小雨"	 ),
TQ15(14	,"中雨"	 ),
TQ16(15	,"大雨"	 ),
TQ17(16	,"暴雨"	),
TQ18(17	,"大暴雨"	 ),
TQ19(18	,"特大暴雨"	 ),
TQ20(19	,"冻雨"	 ),
TQ21(20	,"雨夹雪"	),
TQ22(21	,"阵雪"	 ),
TQ23(22	,"小雪"	 ),
TQ24(23	,"中雪"	 ),
TQ25(24	,"大雪"	 ),
TQ26(25	,"暴雪"	),
TQ27(26	,"浮尘"	),
TQ28(27	,"扬沙"	),
TQ29(28	,"沙尘暴"	),
TQ30(29	,"强沙尘暴"	),
TQ31(30	,"雾"	),
TQ32(31	,"霾"	),
TQ33(32	,"风"	),
TQ34(33	,"大风"	),
TQ35(34	,"飓风"	),
TQ36(35	,"热带风暴"	 ),
TQ37(36	,"龙卷风"	),
TQ38(37	,"冷"	),
TQ39(38	,"热"	),
TQ40(99	,"未知"	);

    String status;
    Integer code;

    WeatherEnums(Integer code, String status) {
        this.status = status;
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
