package com.vp.plugin.sample.deploymentdiagram.actions;

import java.awt.Point;

import com.vp.plugin.ApplicationManager;
import com.vp.plugin.DiagramManager;
import com.vp.plugin.action.VPAction;
import com.vp.plugin.action.VPActionController;
import com.vp.plugin.diagram.ICaptionUIModel;
import com.vp.plugin.diagram.IComponentDiagramUIModel;
import com.vp.plugin.diagram.IDeploymentDiagramUIModel;
import com.vp.plugin.diagram.IDiagramTypeConstants;
import com.vp.plugin.diagram.IShapeUIModel;
import com.vp.plugin.diagram.shape.IArtifactUIModel;
import com.vp.plugin.diagram.shape.IDeploymentSpecificationUIModel;
import com.vp.plugin.diagram.shape.INodeUIModel;
import com.vp.plugin.model.IArtifact;
import com.vp.plugin.model.IDependency;
import com.vp.plugin.model.IDeploymentSpecification;
import com.vp.plugin.model.INode;
import com.vp.plugin.model.factory.IModelElementFactory;

public class DeploymentDiagramActionControl implements VPActionController {

	@Override
	public void performAction(VPAction arg0) {
		// create blank deployment diagram
		DiagramManager diagramManager = ApplicationManager.instance().getDiagramManager();
		IDeploymentDiagramUIModel diagram = (IDeploymentDiagramUIModel) diagramManager.createDiagram(IDiagramTypeConstants.DIAGRAM_TYPE_DEPLOYMENT_DIAGRAM);
		
		// create node model
		INode nodeModel = IModelElementFactory.instance().createNode();
		nodeModel.setName("AppServer");
		// create node shape on diagram
		INodeUIModel nodeShape = (INodeUIModel) diagramManager.createDiagramElement(diagram, nodeModel);
		// specify its location & dimension on diagram
		nodeShape.setBounds(72, 111, 482, 285);
		// set to automatic calculate the initial caption position
		nodeShape.setRequestResetCaption(true);
		
		// create Artifact model
		IArtifact artifactShoppingAppEarModel = IModelElementFactory.instance().createArtifact();
		artifactShoppingAppEarModel.setName("ShoppingApp.ear");
		// add the artifact model as the child of node
		nodeModel.addArtifact(artifactShoppingAppEarModel);
		// create artifact shape on diagram
		IArtifactUIModel artifactShoppingAppEarShape = (IArtifactUIModel) diagramManager.createDiagramElement(diagram, artifactShoppingAppEarModel);
		artifactShoppingAppEarShape.setBounds(106, 176, 419, 190);
		// specify the caption of the artifact shape at top middle
		artifactShoppingAppEarShape.setModelElementNameAlignment(IShapeUIModel.MODEL_ELEMENT_NAME_ALIGNMENT_ALIGN_TOP_MIDDLE);
		// make the artifact shape contained by the node shape
		nodeShape.addChild(artifactShoppingAppEarShape);		
		artifactShoppingAppEarShape.setRequestResetCaption(true);

		
		IArtifact artifactShoppingJar = IModelElementFactory.instance().createArtifact();
		artifactShoppingJar.setName("Shopping.jar");
		artifactShoppingAppEarModel.addArtifact(artifactShoppingJar);
		IArtifactUIModel artifactShoppingJarShape = (IArtifactUIModel) diagramManager.createDiagramElement(diagram, artifactShoppingJar);
		artifactShoppingJarShape.setBounds(136, 219, 111, 40);
		artifactShoppingAppEarShape.addChild(artifactShoppingJarShape);
		artifactShoppingJarShape.setRequestResetCaption(true);
		
		IArtifact artifactOrderJar = IModelElementFactory.instance().createArtifact();
		artifactOrderJar.setName("Order.jar");
		artifactShoppingAppEarModel.addArtifact(artifactOrderJar);
		IArtifactUIModel artifactOrderJarShape = (IArtifactUIModel) diagramManager.createDiagramElement(diagram, artifactOrderJar);
		artifactOrderJarShape.setBounds(373, 219, 93, 40);
		artifactShoppingAppEarShape.addChild(artifactOrderJarShape);
		artifactOrderJarShape.setRequestResetCaption(true);
		
		// create dependency model between Shopping.jar & Order.jar
		IDependency dependencyShoppingJarOrderJar = IModelElementFactory.instance().createDependency();
		// specify the from end (supplier)
		dependencyShoppingJarOrderJar.setFrom(artifactShoppingJar);
		// specify the to end (client)
		dependencyShoppingJarOrderJar.setTo(artifactOrderJar);
		// create dependency connector on diagram
		diagramManager.createConnector(diagram, dependencyShoppingJarOrderJar, artifactShoppingJarShape, artifactOrderJarShape, new Point[] {new Point(247, 239), new Point(373, 239)});				
		
		// create deployment specification model
		IDeploymentSpecification depSpecShoppingAppdescXMLModel = IModelElementFactory.instance().createDeploymentSpecification();
		depSpecShoppingAppdescXMLModel.setName("ShoppingAppdesc.xml");
		// add the deployment specification model as child of artifact model
		artifactShoppingAppEarModel.addDeploymentSpecification(depSpecShoppingAppdescXMLModel);
		// create deployment specification shape
		IDeploymentSpecificationUIModel depSpecShoppingAppdescXMLShape = (IDeploymentSpecificationUIModel) diagramManager.createDiagramElement(diagram, depSpecShoppingAppdescXMLModel);
		depSpecShoppingAppdescXMLShape.setBounds(126, 307, 130, 40);
		// make the artifact shape contain the deployment specification shape
		artifactShoppingAppEarShape.addChild(depSpecShoppingAppdescXMLShape);
		depSpecShoppingAppdescXMLShape.setRequestResetCaption(true);
		
		IDeploymentSpecification depSpecOrderdescXMLModel = IModelElementFactory.instance().createDeploymentSpecification();
		depSpecOrderdescXMLModel.setName("Orderdesc.xml");
		artifactShoppingAppEarModel.addDeploymentSpecification(depSpecOrderdescXMLModel);
		IDeploymentSpecificationUIModel depSpecOrderdescXMLShape = (IDeploymentSpecificationUIModel) diagramManager.createDiagramElement(diagram, depSpecOrderdescXMLModel);
		depSpecOrderdescXMLShape.setBounds(359, 307, 120, 40);
		artifactShoppingAppEarShape.addChild(depSpecOrderdescXMLShape);
		depSpecOrderdescXMLShape.setRequestResetCaption(true);
		
		IDependency dependencyShoppingJarOrderdescXML = IModelElementFactory.instance().createDependency();
		dependencyShoppingJarOrderdescXML.setFrom(depSpecOrderdescXMLModel);
		dependencyShoppingJarOrderdescXML.setTo(artifactOrderJar);
		diagramManager.createConnector(diagram, dependencyShoppingJarOrderdescXML, depSpecOrderdescXMLShape, artifactOrderJarShape, null);
				
		// show up the diagram		
		diagramManager.openDiagram(diagram);
		
	}

	@Override
	public void update(VPAction arg0) {
		// TODO Auto-generated method stub
		
	}

}
