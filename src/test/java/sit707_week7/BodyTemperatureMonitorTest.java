package sit707_week7;

import static org.mockito.Mockito.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BodyTemperatureMonitorTest {

    private TemperatureSensor temperatureSensor;
    private CloudService cloudService;
    private NotificationSender notificationSender;
    private BodyTemperatureMonitor monitor;

    @Before
    public void setUp() {
        temperatureSensor = mock(TemperatureSensor.class);
        cloudService = mock(CloudService.class);
        notificationSender = mock(NotificationSender.class);
        monitor = new BodyTemperatureMonitor(temperatureSensor, cloudService, notificationSender);
    }

    @Test
    public void testReadTemperatureNegative() {
        when(temperatureSensor.readTemperatureValue()).thenReturn(-5.0);
        Assert.assertEquals(-5.0, monitor.readTemperature(), 0.01);
    }

    @Test
    public void testReadTemperatureZero() {
        when(temperatureSensor.readTemperatureValue()).thenReturn(0.0);
        Assert.assertEquals(0.0, monitor.readTemperature(), 0.01);
    }

    @Test
    public void testReadTemperatureNormal() {
        when(temperatureSensor.readTemperatureValue()).thenReturn(36.5);
        Assert.assertEquals(36.5, monitor.readTemperature(), 0.01);
    }

    @Test
    public void testReadTemperatureAbnormallyHigh() {
        when(temperatureSensor.readTemperatureValue()).thenReturn(45.0);
        Assert.assertEquals(45.0, monitor.readTemperature(), 0.01);
    }

    @Test
    public void testReportTemperatureReadingToCloud() {
        TemperatureReading reading = new TemperatureReading();
        monitor.reportTemperatureReadingToCloud(reading);
        verify(cloudService, times(1)).sendTemperatureToCloud(reading);
    }

    @Test
    public void testInquireBodyStatusNormalNotification() {
        when(cloudService.queryCustomerBodyStatus(any(Customer.class))).thenReturn("NORMAL");
        monitor.inquireBodyStatus();
        verify(notificationSender, times(1)).sendEmailNotification(any(Customer.class), eq("Thumbs Up!"));
    }

    @Test
    public void testInquireBodyStatusAbnormalNotification() {
        when(cloudService.queryCustomerBodyStatus(any(Customer.class))).thenReturn("ABNORMAL");
        monitor.inquireBodyStatus();
        verify(notificationSender, times(1)).sendEmailNotification(any(FamilyDoctor.class), eq("Emergency!"));
    }
}
