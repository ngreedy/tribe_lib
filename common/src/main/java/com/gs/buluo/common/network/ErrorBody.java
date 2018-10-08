package com.gs.buluo.common.network;

import java.util.List;

/**
 * Created by DELL on 2018/10/8.
 */

public class ErrorBody {
    public int code;
    public String message;
    public DetailError error;

    class DetailError {
        public int code;
        public String name;
        public String what;
        public List<DetailErrorBean> details;
    }

    class DetailErrorBean {
        public String message;
        public String file;
        public String line_number;
        public String method;
    }
}
