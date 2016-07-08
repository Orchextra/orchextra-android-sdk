package com.gigigo.orchextra.domain.services.actions;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.gggjavalib.business.model.BusinessObject;
import com.gigigo.orchextra.domain.dataprovider.ActionsDataProvider;
import com.gigigo.orchextra.domain.interactors.base.InteractorResponse;
import com.gigigo.orchextra.domain.interactors.error.ServiceErrorChecker;
import com.gigigo.orchextra.domain.model.actions.strategy.BasicAction;
import com.gigigo.orchextra.domain.model.triggers.strategy.types.Trigger;
import com.gigigo.orchextra.domain.model.vo.OrchextraPoint;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class GetActionDomainServiceTest {

    @Mock
    ActionsDataProvider mockActionsDataProvider;

    @Mock
    ServiceErrorChecker mocKServiceErrorChecker;

    @Mock
    BusinessObject<BasicAction> mockBoBasicAction;

    @Mock
    BasicAction mockBasicAction;

    @Mock
    BusinessError mockBusinessError;

    @Mock
    InteractorResponse mockResponse;

    private GetActionDomainService getActionDomainService;

    @Before
    public void setUp() throws Exception {
        getActionDomainService = new GetActionDomainService(mockActionsDataProvider, mocKServiceErrorChecker);
    }

    @Test
    public void shouldReturnEmptyListWhenTriggerListIsEmpty() throws Exception {
        InteractorResponse<List<BasicAction>> actionList = getActionDomainService.getActions(new ArrayList<Trigger>());

        assertNotNull(actionList.getResult());
        assertTrue(actionList.getResult().isEmpty());
    }

    @Test
    public void shouldReturnEmptyListWhenTriggerListDoesntCorrespondsWithAction() throws Exception {
        List<Trigger> triggerList = new ArrayList<>();
        Trigger trigger = Trigger.createBarcodeScanTrigger("1234", new OrchextraPoint());
        triggerList.add(trigger);

        when(mockActionsDataProvider.obtainAction(trigger)).thenReturn(mockBoBasicAction);

        when(mockBoBasicAction.isSuccess()).thenReturn(false);

        when(mockBoBasicAction.getBusinessError()).thenReturn(mockBusinessError);

        when(mocKServiceErrorChecker.checkErrors(mockBusinessError)).thenReturn(mockResponse);

        when(mockResponse.hasError()).thenReturn(false);

        InteractorResponse<List<BasicAction>> actionList = getActionDomainService.getActions(triggerList);

        assertNotNull(actionList.getResult());
        assertTrue(actionList.getResult().isEmpty());
    }

    @Test
    public void shouldReturnOneActionWhenTriggerListCorrespondsWithAction() throws Exception {
        List<Trigger> triggerList = new ArrayList<>();
        Trigger trigger = Trigger.createBarcodeScanTrigger("1234", new OrchextraPoint());
        triggerList.add(trigger);

        when(mockActionsDataProvider.obtainAction(trigger)).thenReturn(mockBoBasicAction);

        when(mockBoBasicAction.isSuccess()).thenReturn(true);

        when(mockBoBasicAction.getData()).thenReturn(mockBasicAction);

        when(mockBoBasicAction.getBusinessError()).thenReturn(mockBusinessError);

        when(mocKServiceErrorChecker.checkErrors(mockBusinessError)).thenReturn(mockResponse);

        when(mockResponse.hasError()).thenReturn(false);

        InteractorResponse<List<BasicAction>> actionList = getActionDomainService.getActions(triggerList);

        assertNotNull(actionList.getResult());
        assertThat(actionList.getResult().get(0), is(mockBasicAction));
    }
}