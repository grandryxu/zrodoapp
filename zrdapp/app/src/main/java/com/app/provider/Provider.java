package com.app.provider;

import com.app.common.DataQueryType;
import com.app.entity.DataResult;

/**
 * Created by grandry.xu on 15-12-29.
 */
public interface Provider {

    public DataResult login(String username, String passwd)throws Exception;

    public DataResult requestForStatisticData(DataQueryType type)throws Exception;

    public DataResult requestForPieChartData(DataQueryType type)throws Exception;

    public DataResult requestForLineChartData(DataQueryType type)throws Exception;

    public DataResult requestForHistoryWarningData()throws Exception;

    public DataResult requestForInfoQueryData(String cardId)throws Exception;

    public DataResult requestForOnTimeWarningData()throws Exception;

    public DataResult postSamplePic(String postdata)throws Exception;



}
