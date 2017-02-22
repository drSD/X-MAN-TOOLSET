package uk.xman.xcore.impl;

import uk.xman.xcore.AtomicComponent;
import uk.xman.xcore.ComputationUnit;
import uk.xman.xcore.XcoreFactory;

public class Test {

	
	private void me() {
	// TODO Auto-generated method stub
		XcoreFactory factory = XcorePackageImpl.eINSTANCE.getXcoreFactory();
		AtomicComponent atomic = factory.createAtomicComponent();
        ComputationUnit cu = factory.createComputationUnit();
    	atomic.setComputationUnit(cu);
	
		
		
	}

	
	
}
