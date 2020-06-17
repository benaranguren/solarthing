package me.retrodaredevil.solarthing.solar.outback;

import com.fasterxml.jackson.core.JsonProcessingException;
import me.retrodaredevil.solarthing.PacketTestUtil;
import me.retrodaredevil.solarthing.solar.event.SolarEventPacket;
import me.retrodaredevil.solarthing.solar.outback.fx.OperationalMode;
import me.retrodaredevil.solarthing.solar.outback.fx.common.FXDailyData;
import me.retrodaredevil.solarthing.solar.outback.fx.common.ImmutableFXDailyData;
import me.retrodaredevil.solarthing.solar.outback.fx.event.*;
import me.retrodaredevil.solarthing.solar.outback.fx.extra.DailyFXPacket;
import me.retrodaredevil.solarthing.solar.outback.fx.extra.ImmutableDailyFXPacket;
import me.retrodaredevil.solarthing.solar.outback.mx.AuxMode;
import me.retrodaredevil.solarthing.solar.outback.mx.ChargerMode;
import me.retrodaredevil.solarthing.solar.outback.mx.event.*;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class OutbackPacketsTest {
	@Test
	void test() throws JsonProcessingException {
		{
			FXDailyData data = new ImmutableFXDailyData(0, System.currentTimeMillis(), 22, 22.3f, 0, 0, 0, 0, Collections.emptySet(), 0, 0, 0, Collections.emptySet());
			DailyFXPacket packet = new ImmutableDailyFXPacket(data);
			assertEquals(22, packet.getDailyMinBatteryVoltage());
			PacketTestUtil.testJson(packet, DailyFXPacket.class);
		}
		{
			FXAuxStateChangePacket packet = new ImmutableFXAuxStateChangePacket(new OutbackIdentifier(1), true, false);
			assertTrue(packet.isAuxActive());
			PacketTestUtil.testJson(packet, FXAuxStateChangePacket.class);
			PacketTestUtil.testJson(packet, SolarEventPacket.class);
		}
		{
			FXOperationalModeChangePacket packet = new ImmutableFXOperationalModeChangePacket(new OutbackIdentifier(1), 3, 4);
			assertEquals(OperationalMode.CHARGE, packet.getOperationalMode());
			PacketTestUtil.testJson(packet, FXOperationalModeChangePacket.class);
			PacketTestUtil.testJson(packet, SolarEventPacket.class);
		}
		{
			MXAuxModeChangePacket packet = new ImmutableMXAuxModeChangePacket(new OutbackIdentifier(1), 3, 0);
			assertEquals(AuxMode.MANUAL, packet.getAuxMode());
			PacketTestUtil.testJson(packet, MXAuxModeChangePacket.class);
			PacketTestUtil.testJson(packet, SolarEventPacket.class);
		}
		{
			MXErrorModeChangePacket packet = new ImmutableMXErrorModeChangePacket(new OutbackIdentifier(1), 32, 0);
			assertEquals(32, packet.getErrorModeValue());
			PacketTestUtil.testJson(packet, MXErrorModeChangePacket.class);
			PacketTestUtil.testJson(packet, SolarEventPacket.class);
		}
		{
			MXChargerModeChangePacket packet = new ImmutableMXChargerModeChangePacket(new OutbackIdentifier(1), 1, 0);
			assertEquals(ChargerMode.FLOAT, packet.getChargingMode());
			PacketTestUtil.testJson(packet, MXChargerModeChangePacket.class);
			PacketTestUtil.testJson(packet, SolarEventPacket.class);
		}
		{
			FXErrorModeChangePacket packet = new ImmutableFXErrorModeChangePacket(new OutbackIdentifier(1), 64, 0);
			assertEquals(64, packet.getErrorModeValue());
			PacketTestUtil.testJson(packet, FXErrorModeChangePacket.class);
			PacketTestUtil.testJson(packet, SolarEventPacket.class);
		}
		{
			FXWarningModeChangePacket packet = new ImmutableFXWarningModeChangePacket(new OutbackIdentifier(1), 32, 0, 0);
			assertEquals(32, packet.getWarningModeValue());
			PacketTestUtil.testJson(packet, FXWarningModeChangePacket.class);
			PacketTestUtil.testJson(packet, SolarEventPacket.class);
		}
	}
}
