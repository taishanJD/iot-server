package com.quarkdata.data.web.api.controller.api;

import com.alibaba.fastjson.JSON;
import com.quarkdata.data.dal.api.UserInfoUtil;
import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.DataSetService;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.web.api.controller.RouteKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
@Controller
@RequestMapping(RouteKey.PREFIX_API + RouteKey.DATASET)
public class DataSetController {

    static Logger logger = Logger.getLogger(DataSetController.class);

    @Autowired
    DataSetService dataSetService;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResultCode addDataSet(@RequestBody String param) {
        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
        ResultCode resultCode = null;
        try {
            Map<String, Object> paramMap = JSON.parseObject(param);
            resultCode = dataSetService.addDataSet(paramMap,createUser);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("创建data_set失败",e);
            resultCode = ResultUtil.error(Messages.ADD_DATASET_FAILED_CODE,Messages.ADD_DATASET_FAILED_MSG);
        }
        return resultCode;
    }

//    @ResponseBody
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    public ResultCode addDataSet(@RequestParam(value = "projectId") Long projectId,
//                                 @RequestParam(value = "dataSourceId") Long dataSourceId,
//                                 @RequestParam(value = "dataSetName") String dataSetName,
//                                 @RequestParam(value = "tableName") String tableName,
//                                 @RequestParam(value = "isFloatToInt") String isFloatToInt) {
////        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
////        Long tenantId = 1l;
//        Long createUser = 1l;
//        ResultCode resultCode;
//        try {
//            resultCode = dataSetService.addDataSet(projectId, dataSourceId, dataSetName, tableName, isFloatToInt, createUser);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error("创建data_set失败", e);
//            resultCode = ResultUtil.error(Messages.ADD_DATASET_FAILED_CODE, Messages.ADD_DATASET_FAILED_MSG);
//        }
//        return resultCode;
//    }

    @ResponseBody
    @RequestMapping(value = "/add_in_workflow", method = RequestMethod.POST)
    public ResultCode addDataSetInWorkflow(@RequestParam(value = "projectId") Long projectId,
                                           @RequestParam(value = "dataSourceId") Long dataSourceId,
                                           @RequestParam(value = "workFlowId") Long workFlowId,
                                           @RequestParam(value = "inputDataSetId") Long inputDataSetId,
                                           @RequestParam(value = "dataSetName") String dataSetName) {
        Long createUser = UserInfoUtil.getUserInfoVO().getUser().getId();
        ResultCode resultCode;
        try {
            resultCode = dataSetService.addDataSetInWorkFlow(projectId, dataSourceId, workFlowId, inputDataSetId, dataSetName, createUser);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("工作流中创建data_set失败", e);
            resultCode = ResultUtil.error(Messages.ADD_DATASET_FAILED_CODE, Messages.ADD_DATASET_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/table_names", method = RequestMethod.GET)
    public ResultCode getTableNames(@RequestParam(value = "dataSourceId") Long dataSourceId) {
        ResultCode resultCode = null;
        try {
            resultCode = dataSetService.getTableNamesFromDataSource(dataSourceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultCode;
    }


    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResultCode getDataSetList(@RequestParam(value = "projectId") Long projectId,
                                     @RequestParam(value = "filter", required = false) String filter,
                                     @RequestParam(value = "workFlowId", required = false) Long workFlowId) {
        ResultCode resultCode;
        try {
            resultCode = dataSetService.getDataSetList(projectId, filter, workFlowId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取数据集列表失败", e);
            resultCode = ResultUtil.error(Messages.GET_DATASET_LIST_FAILED_CODE, Messages.GET_DATASET_LIST_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/data_count", method = RequestMethod.POST)
    public ResultCode getDataCount(@RequestParam(value = "dataSetId") Long dataSetId) {
        ResultCode resultCode;
        try {
            resultCode = dataSetService.getDataSetDataCount(dataSetId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取数据集数据数量失败", e);
            resultCode = ResultUtil.error(Messages.GET_DATASET_DATA_COUNT_FAILED_CODE, Messages.GET_DATASET_DATA_COUNT_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
        @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public ResultCode getDetail(@RequestParam(value = "dataSetId") Long dataSetId) {
        ResultCode resultCode;
        try {
            resultCode = dataSetService.getDataSetDetail(dataSetId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("获取数据集详情失败", e);
            resultCode = ResultUtil.error(Messages.GET_DATASET_DETAIL_FAILED_CODE, Messages.GET_DATASET_DETAIL_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
        @RequestMapping(value = "/detail_by_id", method = RequestMethod.GET)
    public ResultCode getSingleDetailById(@RequestParam(value = "dataSetId") Long dataSetId) {
            return dataSetService.getSingleDetailById(dataSetId);
    }


    @ResponseBody
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ResultCode deleteDataSet(@RequestParam(value = "dataSetIds") String dataSetIds) {
        ResultCode resultCode;
        try {
            resultCode = dataSetService.deleteDataSet(dataSetIds);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("删除数据集失败", e);
            resultCode = ResultUtil.error(Messages.DELETE_DATASET_FAILED_CODE, Messages.DELETE_DATASET_FAILED_MSG);
        }
        return resultCode;
    }


    @ResponseBody
    @RequestMapping(value = "/connect_test", method = RequestMethod.GET)
    public ResultCode testConnect(@RequestParam(value = "dataSourceId") Long dataSourceId,
                                  @RequestParam(value = "tableName") String tableName,
                                  @RequestParam(value = "dataSetId",required = false) Long dataSetId) {
        ResultCode resultCode;
        try {
            resultCode = dataSetService.testConnect(dataSourceId, tableName, dataSetId);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("测试数据集失败", e);
            resultCode = ResultUtil.error(Messages.TABLE_TEST_FAILED_CODE, Messages.TABLE_TEST_FAILED_MSG);
        }
        return resultCode;
    }


    @ResponseBody
    @RequestMapping(value = "/get_source_table_structure", method = RequestMethod.GET)
    public ResultCode getSourceTableStructure(@RequestParam(value = "dataSourceId") Long dataSourceId,
                                        @RequestParam(value = "tableName") String tableName) {
        ResultCode resultCode;
        try {
            resultCode = dataSetService.getSourceTableStructure(dataSourceId, tableName);
        } catch (Exception e) {
            logger.error("获取源表结构失败", e);
            resultCode = ResultUtil.error(Messages.GET_SOURCE_TABLE_STRUCTURE_FAILED_CODE, Messages.GET_SOURCE_TABLE_STRUCTURE_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/clear_data", method = RequestMethod.POST)
    public ResultCode clearData(@RequestParam(value = "dataSetId") Long dataSetId) {
        ResultCode resultCode;
        try {
            resultCode = dataSetService.clearData(dataSetId);
        } catch (Exception e) {
            logger.error("清空数据集数据失败", e);
            resultCode = ResultUtil.error(Messages.DATASET_CLEAR_FAILED_CODE, Messages.DATASET_CLEAR_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/input_list", method = RequestMethod.GET)
    public ResultCode getInputList(@RequestParam(value = "projectId") Long projectId,
                                   @RequestParam(value = "workflowId") Long workflowId,
                                   @RequestParam(value = "page") int page,
                                   @RequestParam(value = "pageSize") int pageSize,
                                   @RequestParam(value = "filter", required = false) String filter) {
        ResultCode resultCode;
        try {
            resultCode = dataSetService.inputList(projectId, workflowId, page, pageSize, filter);
        } catch (Exception e) {
            e.printStackTrace();
            resultCode = ResultUtil.error(Messages.GET_INPUT_LIST_FAILED_CODE, Messages.GET_INPUT_LIST_FAILED_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/output_list", method = RequestMethod.GET)
    public ResultCode getOutputList(@RequestParam(value = "projectId") Long projectId,
                                    @RequestParam(value = "workflowId") Long workflowId,
                                    @RequestParam(value = "page") int page,
                                    @RequestParam(value = "pageSize") int pageSize,
                                    @RequestParam(value = "filter", required = false) String filter) {
        ResultCode resultCode;
        try {
            resultCode = dataSetService.outputList(projectId, workflowId, page, pageSize, filter);
        } catch (Exception e) {
            e.printStackTrace();
            resultCode = ResultUtil.error(Messages.GET_OUTPUT_LIST_FAILED_CODE, Messages.GET_OUTPUT_LIST_FAILED_FAILED_MSG);
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/source_info", method = RequestMethod.GET)
    public ResultCode getDatasourceInfo(@RequestParam(value = "dataSetId") Long dataSetId) {
        ResultCode resultCode = null;
        try {
            resultCode = dataSetService.getDatasourceInfo(dataSetId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultCode;
    }

    @ResponseBody
    @RequestMapping(value = "/is_exist", method = RequestMethod.GET)
    public ResultCode isDatasetNameExist(@RequestParam(value = "datasetName") String datasetName,
                                         @RequestParam(value = "datasourceId") Long datasourceId,
                                         @RequestParam(value = "projectId") Long projectId) {
        ResultCode resultCode = null;
        try {
            resultCode = dataSetService.isDatasetNameExist(datasetName,datasourceId,projectId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultCode;
    }
}
