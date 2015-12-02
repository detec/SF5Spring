package org.openbox.sf5.json.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.service.CriterionService;
import org.openbox.sf5.service.ObjectsListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SettingsJsonizer {

	public HttpStatus saveSetting(Settings setting) {
		long id = setting.getId();
		// if we receive non-empty id
		if (id != 0) {
			return HttpStatus.CONFLICT;
		}

		//Settings DBsetting =
	}

	@SuppressWarnings("unchecked")
	public List<Settings> getSettingsByArbitraryFilter(String fieldName, String typeValue, String login) {
		List<Settings> settList = new ArrayList<>();

		Criterion userCriterion = criterionService.getUserCriterion(login, Settings.class);
		if (userCriterion == null) {
			return settList;
		}

		// building arbitrary criterion
		Criterion arbitraryCriterion = criterionService.getCriterionByClassFieldAndStringValue(Settings.class,
				fieldName, typeValue);

		if (arbitraryCriterion == null) {
			return settList;
		}

		Session session = sessionFactory.openSession();
		Criteria criteria = session.createCriteria(Settings.class).add(userCriterion).add(arbitraryCriterion);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

		settList = criteria.list();

		session.close();

		return settList;

	}

	public List<Settings> getSettingsByUserLogin(String login) {
		List<Settings> settList = new ArrayList<>();

		Criterion userCriterion = criterionService.getUserCriterion(login, Settings.class);
		if (userCriterion == null) {
			return settList;
		}

		settList = listService.ObjectsCriterionList(Settings.class, userCriterion);

		return settList;
	}

	public Settings getSettingById(long settingId, String login) {
		Settings setting = null;

		Criterion userCriterion = criterionService.getUserCriterion(login, Settings.class);
		if (userCriterion == null) {
			return setting;
		}

		Criterion settingIdCriterion = Restrictions.eq("id", settingId);

		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<Settings> records = session.createCriteria(Settings.class).add(userCriterion).add(settingIdCriterion)
				.list();

		if (records.size() == 0) {
			// There is no such setting with username
			return setting;
		} else {
			setting = records.get(0);
		}
		session.close();

		return setting;
	}

	@Autowired
	private ObjectsListService listService;

	@Autowired
	private CriterionService criterionService;

	@Autowired
	private SessionFactory sessionFactory;

	public CriterionService getCriterionService() {
		return criterionService;
	}

	public void setCriterionService(CriterionService criterionService) {
		this.criterionService = criterionService;
	}

	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public ObjectsListService getListService() {
		return listService;
	}

	public void setListService(ObjectsListService listService) {
		this.listService = listService;
	}
}
