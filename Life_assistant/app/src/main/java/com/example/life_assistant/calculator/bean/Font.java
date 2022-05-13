package com.example.life_assistant.calculator.bean;

public class Font {
    private int error_code;//返回码
    private String reason;//返回说明
    private String instr;//输入的字符串
    private String outstr;//转换后的字符串

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getInstr() {
        return instr;
    }

    public void setInstr(String instr) {
        this.instr = instr;
    }

    public String getOutstr() {
        return outstr;
    }

    public void setOutstr(String outstr) {
        this.outstr = outstr;
    }
}
