package org.ednovo.gooru.controllers.v2.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.ArrayUtils;
import org.ednovo.gooru.controllers.BaseController;
import org.ednovo.gooru.core.api.model.ActionResponseDTO;
import org.ednovo.gooru.core.api.model.Country;
import org.ednovo.gooru.core.api.model.Province;
import org.ednovo.gooru.core.constant.ConstantProperties;
import org.ednovo.gooru.core.constant.GooruOperationConstants;
import org.ednovo.gooru.core.constant.ParameterProperties;
import org.ednovo.gooru.core.security.AuthorizeOperations;
import org.ednovo.gooru.domain.service.CountryService;
import org.ednovo.goorucore.application.serializer.JsonDeserializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = { "/v2/country" })
public class CountryRestV2Controller extends BaseController implements ConstantProperties, ParameterProperties {

	@Autowired
	public CountryService countryService;

	@AuthorizeOperations(operations = { GooruOperationConstants.OPERATION_SCOLLECTION_ADD })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@RequestMapping(value = { " " }, method = RequestMethod.POST)
	public ModelAndView createCountry(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionResponseDTO<Country> responseDTO = getCountryService().createCountry(buildCollectionFromInputParameters(data));
		if (responseDTO.getErrors().getErrorCount() > 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		String includes[] = (String[]) ArrayUtils.addAll(COUNTRY, ERROR_INCLUDE);
		return toModelAndViewWithIoFilter(responseDTO.getModelData(), RESPONSE_FORMAT_JSON, EXCLUDE_ALL, true, includes);
	}

	@AuthorizeOperations(operations = { GooruOperationConstants.OPERATION_SCOLLECTION_ADD })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.PUT)
	public ModelAndView updateCountry(@PathVariable(value = ID) String countryId, @RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return toModelAndViewWithIoFilter(getCountryService().updateCountry(countryId, buildCollectionFromInputParameters(data)), RESPONSE_FORMAT_JSON, EXCLUDE_ALL, true, COUNTRY);
	}

	@AuthorizeOperations(operations = { GooruOperationConstants.OPERATION_SCOLLECTION_ADD })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.GET)
	public ModelAndView getCountry(@PathVariable(value = ID) String countryId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return toModelAndViewWithIoFilter(getCountryService().getCountry(countryId), RESPONSE_FORMAT_JSON, EXCLUDE_ALL, true, COUNTRY);
	}

	@AuthorizeOperations(operations = { GooruOperationConstants.OPERATION_SCOLLECTION_ADD })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@RequestMapping(value = { " " }, method = RequestMethod.GET)
	public ModelAndView getCountries(@RequestParam(value = OFFSET_FIELD, required = false, defaultValue = "0") Integer offset, @RequestParam(value = LIMIT_FIELD, required = false, defaultValue = "10") Integer limit, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return toModelAndViewWithIoFilter(getCountryService().getCountries(limit, offset), RESPONSE_FORMAT_JSON, EXCLUDE_ALL, true, COUNTRY);
	}

	@AuthorizeOperations(operations = { GooruOperationConstants.OPERATION_SCOLLECTION_ADD })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@RequestMapping(value = { "/{id}" }, method = RequestMethod.DELETE)
	public void deleteCountry(@PathVariable(value = ID) String countryId, HttpServletRequest request, HttpServletResponse response) {
		getCountryService().deleteCountry(countryId);
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

	// State method creation

	@AuthorizeOperations(operations = { GooruOperationConstants.OPERATION_SCOLLECTION_ADD })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@RequestMapping(value = { "/{id}/state " }, method = RequestMethod.POST)
	public ModelAndView createState(@PathVariable(value = ID) String countryId ,String stateId, @RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws Exception {
		ActionResponseDTO<Province> responseDTO = getCountryService().createState(buildProvinceFromInputParameters(data),countryId);
		if (responseDTO.getErrors().getErrorCount() > 0) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		} else {
			response.setStatus(HttpServletResponse.SC_CREATED);
		}
		String includes[] = (String[]) ArrayUtils.addAll(COUNTRY, ERROR_INCLUDE);
		return toModelAndViewWithIoFilter(responseDTO.getModelData(), RESPONSE_FORMAT_JSON, EXCLUDE_ALL, true, includes);
	}

	@AuthorizeOperations(operations = { GooruOperationConstants.OPERATION_SCOLLECTION_ADD })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@RequestMapping(value = { "/{id}/state/{sid}" }, method = RequestMethod.PUT)
	public ModelAndView updateState(@PathVariable(value = ID) String countryId,@PathVariable(value = SID) String stateId, @RequestBody String data, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return toModelAndViewWithIoFilter(getCountryService().updateState(countryId,stateId,buildStateInputParameters(data)), RESPONSE_FORMAT_JSON, EXCLUDE_ALL, true, COUNTRY);
	}

	@AuthorizeOperations(operations = { GooruOperationConstants.OPERATION_SCOLLECTION_ADD })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@RequestMapping(value = { "/{id}/state/{sid}" }, method = RequestMethod.GET)
	public ModelAndView getState(@PathVariable(value = ID) String countryId,@PathVariable(value = SID) String stateId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return toModelAndViewWithIoFilter(getCountryService().getState(countryId,stateId), RESPONSE_FORMAT_JSON, EXCLUDE_ALL, true, COUNTRY);
	}

	@AuthorizeOperations(operations = { GooruOperationConstants.OPERATION_SCOLLECTION_ADD })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@RequestMapping(value = { "{id}/state" }, method = RequestMethod.GET)
	public ModelAndView getStates(@PathVariable(value = ID) String countryId,@RequestParam(value = OFFSET_FIELD, required = false, defaultValue = "0") Integer offset, @RequestParam(value = LIMIT_FIELD, required = false, defaultValue = "10") Integer limit, HttpServletRequest request, HttpServletResponse response) throws Exception {
		return toModelAndViewWithIoFilter(getCountryService().getStates(countryId,limit, offset), RESPONSE_FORMAT_JSON, EXCLUDE_ALL, true, COUNTRY);
	}

	@AuthorizeOperations(operations = { GooruOperationConstants.OPERATION_SCOLLECTION_ADD })
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
	@RequestMapping(value = { "/{id}/state/{sid}" }, method = RequestMethod.DELETE)
	public void deleteState(@PathVariable(value = ID) String countryId,@PathVariable(value = SID)  String stateId, HttpServletRequest request, HttpServletResponse response) {
		getCountryService().deleteState(countryId,stateId);
		response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

	private Province buildProvinceFromInputParameters(String data) {
		return JsonDeserializer.deserialize(data, Province.class);
	}

	private Country buildCollectionFromInputParameters(String data) {
		return JsonDeserializer.deserialize(data, Country.class);
	}
	private Province buildStateInputParameters(String data) {
		return JsonDeserializer.deserialize(data, Province.class);
	}
	public CountryService getCountryService() {
		return countryService;
	}

}
