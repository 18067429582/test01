package com.bjpowernode.VO;

public class QueryParam {
    private Integer paramId;
    private String paramName;

    public QueryParam() {
    }

    public QueryParam(Integer paramId, String paramName) {
        this.paramId = paramId;
        this.paramName = paramName;
    }

    public Integer getParamId() {
        return paramId;
    }

    public void setParamId(Integer paramId) {
        this.paramId = paramId;
    }

    public String getParamName() {
        return paramName;
    }

    public void setParamName(String paramName) {
        this.paramName = paramName;
    }

    @Override
    public String toString() {
        return "QueryParam{" +
                "paramId=" + paramId +
                ", paramName='" + paramName + '\'' +
                '}';
    }
}
