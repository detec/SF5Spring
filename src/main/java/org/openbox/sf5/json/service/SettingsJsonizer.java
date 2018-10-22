package org.openbox.sf5.json.service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.openbox.sf5.dao.CriterionService;
import org.openbox.sf5.dao.ObjectsController;
import org.openbox.sf5.model.Settings;
import org.openbox.sf5.model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class SettingsJsonizer {

    @Autowired
    private CriterionService criterionService;

    @Autowired
    private ObjectsController objectsController;

	public HttpStatus saveNewSetting(Settings setting) {
		long id = setting.getId();
		// if we receive non-empty id
		if (id != 0) {
			return HttpStatus.CONFLICT;
		}
		objectsController.saveOrUpdate(setting);
		return HttpStatus.CREATED;
	}

	@SuppressWarnings("unchecked")
	public List<Settings> getSettingsByArbitraryFilter(String fieldName, String typeValue, Users user) {
		Criterion userCriterion = Restrictions.eq("User", user);

		// building arbitrary criterion
		Criterion arbitraryCriterion = criterionService.getCriterionByClassFieldAndStringValue(Settings.class,
				fieldName, typeValue);
		if (arbitraryCriterion == null) {
            return Collections.emptyList();
		}

		Session session = objectsController.openSession();
		Criteria criteria = session.createCriteria(Settings.class).add(userCriterion).add(arbitraryCriterion);
		criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);

        List<Settings> settList = criteria.list();
		session.close();
		return settList;
	}

	public List<Settings> getSettingsByUser(Users user) {
        return Optional.ofNullable(Restrictions.eq("User", user))
                .map(cr -> objectsController.restrictionList(Settings.class, cr)).orElse(Collections.emptyList());
	}

	public List<Settings> getSettingsByUserLogin(String login) {
        return Optional.ofNullable(criterionService.getUserCriterion(login, Settings.class))
                .map(cr -> objectsController.restrictionList(Settings.class, cr)).orElse(Collections.emptyList());
	}

	public Settings getSettingById(long settingId, Users user) {
		Criterion userCriterion = Restrictions.eq("User", user);
		Criterion settingIdCriterion = Restrictions.eq("id", settingId);

		Session session = objectsController.openSession();
		@SuppressWarnings("unchecked")
        Settings setting = (Settings) session.createCriteria(Settings.class).add(userCriterion).add(settingIdCriterion)
                .list().stream().map(Settings.class::cast).findFirst().orElse(null);

		session.close();
		return setting;
	}

	public ObjectsController getObjectsController() {
		return objectsController;
	}

	public void setObjectsController(ObjectsController objectsController) {
		this.objectsController = objectsController;
	}

	public CriterionService getCriterionService() {
		return criterionService;
	}

	public void setCriterionService(CriterionService criterionService) {
		this.criterionService = criterionService;
	}
}
