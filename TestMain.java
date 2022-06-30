package NewDB;

public class TestMain {
	public static void main(String[] args) {
		NewShiftSchedule NewShiftSchedule = new NewShiftSchedule();
		NewRunDelivery NewRunDelivery=new NewRunDelivery();
		UpdatePackage UpdatePackage=new UpdatePackage();
		
		//NewShiftSchedule.shiftNationPlane();ok
		//NewShiftSchedule.shiftDomesticPlane();ok
		//NewShiftSchedule.shiftDomesticTrunk();ok
		//NewShiftSchedule.shiftProvinceTrunk();ok
		//NewShiftSchedule.shiftLocalTrunk();
		//NewShiftSchedule.shiftWarehouse();ok
		
		//NewRunDelivery.runAllOrder();
		
		//UpdatePackage.updatePackageDetails();

		GuiFrontPage gui1 = new GuiFrontPage();
		gui1.initialize();
		gui1.setVisible(true);
		
	}
}
