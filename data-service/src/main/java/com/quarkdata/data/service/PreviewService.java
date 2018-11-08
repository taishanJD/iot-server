package com.quarkdata.data.service;

import com.quarkdata.data.model.common.ResultCode;

import java.text.ParseException;
import java.util.Map;

public interface PreviewService {

    /**
     * 获取采样方式和过滤条件
     *
     * @param dataSetId
     * @return
     */
    ResultCode getSampleAndFilterMethod(Long dataSetId);

    /**
     * 更新数据集预览抽样和过滤方式
     *
     * @param paramMap json 例子：
     * {
     * 	"dataSetId": 1,
     * 	"sampleType": 2, //采样方式（0：前n条记录、1：随机n条记录（按个数）、2：随机n条记录（按比例））默认0
     * 	"sampleTypeValue": 1000,//采样数量（采样个数、采样比例）默认采样个数10000
     * 	"isSampleFilter": 0,//是否采样过滤（0：否、1：是）,如果为0则没有下面两项
     * 	"sampleFilterType": 1,//采样过滤方式（0：以下情况均符合，1：至少有一种情况符合）
     * 	"filters": [{ //具体过滤方式
     * 			"columnName": "id",
     * 			"condition": 2,//条件(0：不为空，1：为空，2：==，3：!=，4：>，5：<，6：>=，7：<=，8：等于，9：不等于，10：包含，11：介于，12：早于，13：晚于，14：true，15：false)；
     * 			"value1": 12345
     * 		},
     * 		{
     * 			"columnName": "create_time",
     * 			"condition": 11,
     * 			"value1": "2018 - 05 - 04 12: 34: 56",
     * 			"value2": "2018 - 05 - 05 12: 34: 56"
     * 		},
     * 		{
     * 			"columnName": "table_name",
     * 			"condition": 1
     * 		}
     * 	]
     * }
     *
     *
     * @return
     */
    ResultCode updateSampleFilterMethod(Map<String, Object> paramMap);

    /**
     * 同步数据
     * 重新从源表按照采样和过滤规则拉取数据，放到mongo中
     *
     * @param dataSetId
     */
    ResultCode syncData(Long dataSetId);

    /**
     * 同步语义
     * 重新计算语义占比，并更新字段
     *
     * @param dataSetId
     */
    ResultCode syncProportion(Long dataSetId);

    /**
     * mongo中是否有数据
     *
     * @param dataSetId
     */
    boolean isMongoDataExist(Long dataSetId);

    /**
     * 获取数据
     * 从mongo中分页获取数据
     *
     * @param dataSetId
     */
    ResultCode getData(Long dataSetId, Integer pageNum, Integer pageSize, String filter);
}
