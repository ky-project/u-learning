package com.ky.ulearning.common.core.constant;

/**
 * 文件类型枚举
 * 根据文件流前n位字节识别
 *
 * @author luyuhao
 * @since 20/01/29 02:49
 */
public enum  FileTypeEnum {
    /**
     * 文件类型->文件流头
     */
    /** 已测 */
    JPG("ffd8ff"),
    PNG("89504e47"),
    GIF("47494638"),
    JPEG("ffd8ff"),
    MP4("00000020"),
    /** 待测 */
    TIF("49492a00"),
    BMP("424d"),
    DWG("41433130"),
    HTML("3c21444f"),
    HTM("3c21646f"),
    CSS("48544d4c"),
    JS("696b2e71"),
    RTF("7b5c7274"),
    PSD("38425053"),
    EML("46726f6d"),
    DOC("d0cf11e0"),
    VSD("d0cf11e0"),
    MDB("5374616E"),
    PS("252150532D41646F6265"),
    PDF("255044462D312E"),
    RMVB("2e524d46"),
    RM("2e524d46"),
    FLV("464c5601"),
    F4V("464c5601"),
    MP3("49443303"),
    MPG("000001ba"),
    WMV("3026b275"),
    ASF("3026b275"),
    WAV("52494646"),
    AVI("52494646"),
    MID("4d546864"),
    ZIP("504b0304"),
    RAR("52617221"),
    INI("23546869"),
    JAR("504b0304"),
    EXE("4d5a9000"),
    JSP("3c254020"),
    MF("4d616e69"),
    XML("3c3f786d"),
    SQL("494e5345"),
    JAVA("7061636b"),
    BAT("40656368"),
    GZ("1f8b0800"),
    PROPERTIES("6c6f6734"),
    CLASS("cafebabe"),
    CHM("49545346"),
    MXP("04000000"),
    DOCX("504b0304"),
    WPS("d0cf11e0"),
    TORRENT("6431303a"),
    MOV("6D6F6F76"),
    WPD("FF575043"),
    DBX("CFAD12FEC5FD746F"),
    PST("2142444E"),
    QDF("AC9EBD8F"),
    PWL("E3828596"),
    RAM("2E7261FD");

    private String value;

    FileTypeEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
