package com.infotech.adb.api.ref;



import com.fasterxml.jackson.core.JsonProcessingException;
import com.infotech.adb.dto.BCADTO;
import com.infotech.adb.dto.CancellationOfFTDTO;
import com.infotech.adb.dto.ReversalOfBdaBcaDTO;
import com.infotech.adb.exceptions.CustomException;
import com.infotech.adb.exceptions.DataValidationException;
import com.infotech.adb.exceptions.NoDataFoundException;
import com.infotech.adb.exceptions.PSWAPIException;
import com.infotech.adb.model.entity.BCA;
import com.infotech.adb.model.entity.CancellationOfFT;
import com.infotech.adb.model.entity.ReversalOfBdaBca;
import com.infotech.adb.service.ReferenceService;
import com.infotech.adb.util.AppConstants;
import com.infotech.adb.util.AppUtility;
import com.infotech.adb.util.CustomResponse;
import com.infotech.adb.util.ResponseUtility;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.ResourceBundle;


@RestController
@RequestMapping("/reversal-of")
@Log4j2
public class ReversalOfBdaBcaAPI {

    private static final ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @Autowired
    private ReferenceService referenceService;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public CustomResponse getLastModifiedReversal()
            throws CustomException, NoDataFoundException {

        List<ReversalOfBdaBca> refList = null;
        try {
            refList = referenceService.getLastModifiedReversal();
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseList(refList, new ReversalOfBdaBcaDTO());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomResponse getReversalById(@PathVariable Long id)
            throws CustomException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(id)) {
            throw new DataValidationException(messageBundle.getString("id.not.found"));
        }
        ReversalOfBdaBca entity = null;
        try {
            entity = referenceService.getReversalById(id);
        } catch (Exception e) {
            throw new CustomException(e);
        }
        return ResponseUtility.buildResponseObject(entity, new ReversalOfBdaBcaDTO(),true);
    }

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public CustomResponse updateReversal(@RequestBody ReversalOfBdaBcaDTO reqDTO, @RequestParam(value = "pushToPSW",defaultValue = "false", required = false) Boolean pushToPSW)
            throws PSWAPIException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || AppUtility.isEmpty(reqDTO.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }

        CustomResponse customResponse = null;
        ReversalOfBdaBca entity = null;
        try {
            if (pushToPSW) {
                customResponse = ResponseUtility.translatePSWAPIResponse(referenceService.updateReveralBCABDAAndShare(reqDTO));
            } else {
                entity = referenceService.updateReversalBDABCA(reqDTO.convertToEntity());
                customResponse = ResponseUtility.successResponse(entity,"200","Record Updated Successfully");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customResponse;
    }

    @RequestMapping(value = "/search-reversal-bda-bca", method = RequestMethod.GET)
    public CustomResponse searchReversalOfBdaBca(HttpServletRequest request,
                                                 @RequestParam(value = "tradeType", required = false) String tradeType,
                                                 @RequestParam(value = "traderNTN", required = false) String traderNTN,
                                                 @RequestParam(value = "fromDate", required = false) String fromDate,
                                                 @RequestParam(value = "toDate", required = false) String toDate,
                                                 @RequestParam(value = "isNew", defaultValue = "true", required = false) boolean isNew)
            throws CustomException, NoDataFoundException {

        List<ReversalOfBdaBca> reversalOfBdaBcaList = null;
        try {
            reversalOfBdaBcaList = referenceService.searchReversalOfBDABCA(tradeType, traderNTN, fromDate, toDate
                    , AppConstants.RecordStatuses.getSearchStatesList(isNew));
        } catch (Exception e) {
            ResponseUtility.exceptionResponse(e);
        }
        return ResponseUtility.buildResponseList(reversalOfBdaBcaList, new ReversalOfBdaBcaDTO());
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public CustomResponse createReversalOfBDAAndBCA(HttpServletRequest request,
                                    @RequestBody ReversalOfBdaBcaDTO reqDTO,
                                    @RequestParam(value = "pushToPSW", defaultValue = "false", required = false) Boolean pushToPSW)
            throws PSWAPIException, DataValidationException, NoDataFoundException {

        if (AppUtility.isEmpty(reqDTO) || !AppUtility.isEmpty(reqDTO.getId())) {
            throw new DataValidationException(messageBundle.getString("validation.error"));
        }
        CustomResponse customResponse = null;
        ReversalOfBdaBca entity = null;
        reqDTO.setStatus(AppConstants.RecordStatuses.NEW);
        try {
            if (pushToPSW) {
                customResponse = ResponseUtility.translatePSWAPIResponse(referenceService.updateReveralBCABDAAndShare(reqDTO));
            } else {
                entity = referenceService.createReversalBDABCA(reqDTO.convertToEntity());
                customResponse = ResponseUtility.successResponse(entity, "200", "Record Created Successfully");
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return customResponse;
    }
}
