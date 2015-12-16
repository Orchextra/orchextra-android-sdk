package gigigo.com.orchextra.data.datasources.api.model.mappers;

import com.gigigo.gggjavalib.business.model.BusinessError;
import com.gigigo.ggglib.network.mappers.Mapper;
import com.gigigo.ggglib.network.responses.ApiGenericExceptionResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;

import gigigo.com.orchextra.data.datasources.api.model.responses.base.OrchextraApiErrorResponse;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class OrchextraGenericResponseMapperTest {

    @Mock
    Mapper mapper;

    private OrchextraGenericResponseMapper responseMapper;

    @Before
    public void setUp() throws Exception {
        responseMapper = new OrchextraGenericResponseMapper(mapper);
    }

    @Test
    public void testCreateBusinessErrorOk() throws Exception {
        OrchextraApiErrorResponse response = new OrchextraApiErrorResponse(404, "Not Found");

        BusinessError businessError = responseMapper.createBusinessError(response);

        assertNotNull(businessError);
        assertEquals(404, businessError.getCode());
        assertEquals("Not Found", businessError.getMessage());
    }

    @Test
    public void testOnExceptionOk() throws Exception {
        ApiGenericExceptionResponse response = new ApiGenericExceptionResponse(new NullPointerException("Null Pointer Exception"));

        BusinessError businessError = responseMapper.onException(response);

        assertNotNull(businessError);
        assertEquals(BusinessError.EXCEPTION_BUSINESS_ERROR_CODE, businessError.getCode());
        assertEquals("Null Pointer Exception", businessError.getMessage());
    }
}
