package cn.itcast.mybatis.bean;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class EasyUIResult {
    
    //定义JackSon对象
    private static final ObjectMapper MAPPER = new ObjectMapper();

    private Long total;
    
    private List<?> rows;
    
    public EasyUIResult(){}

    public EasyUIResult(Long total, List<?> rows) {
        this.total = total;
        this.rows = rows;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<?> getRows() {
        return rows;
    }

    public void setRows(List<?> rows) {
        this.rows = rows;
    }
    
    
}
