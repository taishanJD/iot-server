package com.quarkdata.data.web.api.controller.api;

import com.quarkdata.data.model.common.Messages;
import com.quarkdata.data.model.common.ResultCode;
import com.quarkdata.data.service.ProcessByCodeService;
import com.quarkdata.data.util.ResultUtil;
import com.quarkdata.data.web.api.controller.RouteKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.handler.MessageContext;

@Controller
@RequestMapping(RouteKey.PREFIX_API + RouteKey.CODE_PROCESS)
public class ProcessByCodeController {

    @Autowired
    ProcessByCodeService processByCodeService;

    @ResponseBody
    @RequestMapping(value = "/validate_sql", method = RequestMethod.POST)
    public ResultCode parseSql(@RequestParam(value = "sql") String sql,
                               @RequestParam(value = "inputIds") String inputIds) {
        return processByCodeService.validateSql(sql, inputIds);
    }

    @ResponseBody
    @RequestMapping(value = "/execute_sql", method = RequestMethod.POST)
    public ResultCode executeSql(@RequestParam(value = "sql") String sql,
                                 @RequestParam(value = "inputIds") String inputIds,
                                 @RequestParam(value = "outputId") Long outputId,
                                 @RequestParam(value = "processNodeId") Long processNodeId,
                                 @RequestParam(value = "projectId") Long projectId) {
        return processByCodeService.executeSql(sql, inputIds, outputId, processNodeId, projectId);
    }

    @ResponseBody
    @RequestMapping(value = "/save_sql", method = RequestMethod.POST)
    public ResultCode saveSql(@RequestParam(value = "workflowId") Long workflowId,
                              @RequestParam(value = "sql") String sql,
                              @RequestParam(value = "inputIds") String inputIds,
                              @RequestParam(value = "outputId") Long outputId,
                              @RequestParam(value = "isAppend") String isAppend,
                              @RequestParam(value = "processNodeId") Long processNodeId,
                              @RequestParam(value = "projectId") Long projectId) {
        ResultCode resultCode;
        try {
            resultCode = processByCodeService.savaSql(workflowId, sql, inputIds, outputId, isAppend, processNodeId, projectId);
            return resultCode;
        } catch (Exception e) {
            e.printStackTrace();
            resultCode = ResultUtil.error(Messages.SAVE_CODE_PROCESS_FAILED_CODE, Messages.SAVE_CODE_PROCESS_FAILED_MSG);
            return resultCode;
        }

    }

    @ResponseBody
    @RequestMapping(value = "/get_sql", method = RequestMethod.GET)
    public ResultCode getSql(@RequestParam(value = "nodeId") Long nodeId) {
        return processByCodeService.getSql(nodeId);
    }
}
