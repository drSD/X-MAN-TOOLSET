package fxman.productexplorer.views;

import uk.fxman.Aggregation;
import uk.fxman.FxmanFactory;
import uk.fxman.Product;
import uk.fxman.ProductFamily;
import uk.fxman.TupleXMANSet;
import uk.fxman.XMANArchitecture;
import uk.fxman.XMANSet;
import uk.xman.xcore.Aggregator;
import uk.xman.xcore.CoordinationConnection;
import uk.xman.xcore.XcoreFactory;

public class VarGenUtils {
	/**
	 * @param pfs
	 * @param variant
	 */
	public static ProductFamily  convert(Object variant) {
		if (variant instanceof TupleXMANSet) {
			ProductFamily pf = FxmanFactory.eINSTANCE.createProductFamily();		
			// flatten all the XMANSet in the tuple
			for (XMANSet xmanSet : ((TupleXMANSet) variant).getSets()) {
				if (xmanSet.isEmpty()) {
					Product p = FxmanFactory.eINSTANCE.createProduct();
					pf.add(p);
				}
				else {
					// create a product for each XMANArchitecture in the XMANSet
					for (XMANArchitecture arch : xmanSet.getMembers()) {
						// create a product and populate it with Aggregator
						Product p = FxmanFactory.eINSTANCE.createProduct();
						if (arch instanceof Aggregation) {
							p.setName("Agg");
							// add the Aggregator connector
							Aggregator aggr = XcoreFactory.eINSTANCE.createAggregator();
							p.getComposables().add(aggr);
							for (XMANArchitecture childArch : ((Aggregation) arch).getMembers()) {
								// add the XMAN architecture
								p.add(childArch);
								
								// create a coordination connection linking Aggregator and the
								// architecture
								CoordinationConnection coord = XcoreFactory.eINSTANCE.createCoordinationConnection();
								coord.setSource(aggr);
								coord.setTarget(childArch);
								aggr.getConnections().add(coord);
								// add the coordination connection
								p.getConnections().add(coord);
							}
						}
						else {
							p.setName(arch.getName());
							p.add(arch);
						}
						
						// add the product into the product family
						pf.add(p);
					}
				}
			}
			
			return pf;
		}
		else if (variant instanceof XMANSet) {
			ProductFamily pf = FxmanFactory.eINSTANCE.createProductFamily();
			
			XMANSet xmanSet = ((XMANSet) variant);
			// if the XMANSet is empty - as generated from Optional operator
			// create a respective Product
			if (xmanSet.isEmpty()) {
				Product p = FxmanFactory.eINSTANCE.createProduct();
				// add the product into the product family
				pf.add(p);
			}
			// otherwise create a Product per XMANArchitecture in non empty XMANSet
			else {
				// create a product for each XMANArchitecture in the XMANSet
				for (XMANArchitecture arch : xmanSet.getMembers()) {
					// create a product and populate it with Aggregator
					Product p = FxmanFactory.eINSTANCE.createProduct();
					if (arch instanceof Aggregation) {
						p.setName("Agg");
						Aggregator aggr = XcoreFactory.eINSTANCE.createAggregator();
						// add the Aggregator connector
						p.getComposables().add(aggr);
						for (XMANArchitecture childArch : ((Aggregation) arch).getMembers()) {
							// add the XMAN architecture
							p.add(childArch);
							// create a coordination connection linking Aggregator and the
							// architecture
							CoordinationConnection coord = XcoreFactory.eINSTANCE.createCoordinationConnection();
							coord.setSource(aggr);
							coord.setTarget(childArch);
							aggr.getConnections().add(coord);
							// add the coordination connection
							p.getConnections().add(coord);
						}
					}
					else {
						p.setName(arch.getName());
						p.add(arch);
					}
					
					// add the product into the product family
					pf.add(p);
				}
			}
			
			return pf;
		}
		return null;
	}
}
