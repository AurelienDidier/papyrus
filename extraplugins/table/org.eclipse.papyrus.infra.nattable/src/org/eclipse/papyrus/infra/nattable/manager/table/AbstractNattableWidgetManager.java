/*****************************************************************************
 * Copyright (c) 2012 CEA LIST.
 *
 *    
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *  Vincent Lorenzo (CEA LIST) vincent.lorenzo@cea.fr - Initial API and implementation
 *
 *****************************************************************************/
package org.eclipse.papyrus.infra.nattable.manager.table;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.dnd.LocalTransfer;
import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.AbstractRegistryConfiguration;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.EditableRule;
import org.eclipse.nebula.widgets.nattable.config.IConfigRegistry;
import org.eclipse.nebula.widgets.nattable.coordinate.Range;
import org.eclipse.nebula.widgets.nattable.copy.command.CopyDataToClipboardCommand;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.edit.EditConfigAttributes;
import org.eclipse.nebula.widgets.nattable.export.command.ExportCommand;
import org.eclipse.nebula.widgets.nattable.grid.GridRegion;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayerListener;
import org.eclipse.nebula.widgets.nattable.layer.LabelStack;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent;
import org.eclipse.nebula.widgets.nattable.print.command.PrintCommand;
import org.eclipse.nebula.widgets.nattable.print.command.TurnViewportOffCommand;
import org.eclipse.nebula.widgets.nattable.print.command.TurnViewportOnCommand;
import org.eclipse.nebula.widgets.nattable.print.config.DefaultPrintBindings;
import org.eclipse.nebula.widgets.nattable.reorder.ColumnReorderLayer;
import org.eclipse.nebula.widgets.nattable.reorder.event.ColumnReorderEvent;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectAllCommand;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectColumnCommand;
import org.eclipse.nebula.widgets.nattable.selection.command.SelectRowsCommand;
import org.eclipse.nebula.widgets.nattable.style.DisplayMode;
import org.eclipse.nebula.widgets.nattable.ui.binding.UiBindingRegistry;
import org.eclipse.papyrus.infra.core.services.ServiceException;
import org.eclipse.papyrus.infra.core.services.ServicesRegistry;
import org.eclipse.papyrus.infra.emf.utils.ServiceUtilsForEObject;
import org.eclipse.papyrus.infra.nattable.Activator;
import org.eclipse.papyrus.infra.nattable.configuration.CornerConfiguration;
import org.eclipse.papyrus.infra.nattable.configuration.PapyrusClickSortConfiguration;
import org.eclipse.papyrus.infra.nattable.configuration.PapyrusHeaderMenuConfiguration;
import org.eclipse.papyrus.infra.nattable.dataprovider.AbstractDataProvider;
import org.eclipse.papyrus.infra.nattable.dataprovider.BodyDataProvider;
import org.eclipse.papyrus.infra.nattable.dataprovider.ColumnHeaderDataProvider;
import org.eclipse.papyrus.infra.nattable.dataprovider.RowHeaderDataProvider;
import org.eclipse.papyrus.infra.nattable.layer.PapyrusGridLayer;
import org.eclipse.papyrus.infra.nattable.layerstack.BodyLayerStack;
import org.eclipse.papyrus.infra.nattable.layerstack.ColumnHeaderLayerStack;
import org.eclipse.papyrus.infra.nattable.layerstack.RowHeaderLayerStack;
import org.eclipse.papyrus.infra.nattable.listener.NatTableDropListener;
import org.eclipse.papyrus.infra.nattable.manager.cell.CellManagerFactory;
import org.eclipse.papyrus.infra.nattable.model.nattable.Table;
import org.eclipse.papyrus.infra.nattable.model.nattable.nattableaxis.IAxis;
import org.eclipse.papyrus.infra.nattable.provider.PapyrusNatTableToolTipProvider;
import org.eclipse.papyrus.infra.nattable.provider.TableSelectionProvider;
import org.eclipse.papyrus.infra.nattable.sort.ColumnSortModel;
import org.eclipse.papyrus.infra.nattable.sort.IPapyrusSortModel;
import org.eclipse.papyrus.infra.nattable.utils.AxisUtils;
import org.eclipse.papyrus.infra.nattable.utils.LocationValue;
import org.eclipse.papyrus.infra.nattable.utils.NattableConfigAttributes;
import org.eclipse.papyrus.infra.nattable.utils.TableGridRegion;
import org.eclipse.papyrus.infra.services.labelprovider.service.LabelProviderService;
import org.eclipse.papyrus.infra.widgets.util.IRevealSemanticElement;
import org.eclipse.swt.dnd.DND;
import org.eclipse.swt.dnd.DropTarget;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPartSite;

/**
 * 
 * This class allows to create, configure and manipulate the NatTable Widget
 * 
 */
public abstract class AbstractNattableWidgetManager implements INattableModelManager, IRevealSemanticElement {

	/**
	 * the managed table
	 */
	private Table table;

	/**
	 * we need to keep it, to be able to remove the listener on it, when the table is destroying
	 */
	private EObject tableContext;

	/**
	 * the nattable widget
	 */
	protected NatTable natTable;

	/**
	 * the grid layer
	 */
	private GridLayer gridLayer;

	/**
	 * the columnHeaderLayerStack
	 */
	private ColumnHeaderLayerStack columnHeaderLayerStack;

	/**
	 * the rowHeaderLayerStack
	 */
	private RowHeaderLayerStack rowHeaderLayerStack;

	/**
	 * the selection provider
	 */
	private TableSelectionProvider selectionProvider;

	/**
	 * the body layer stack
	 */
	private BodyLayerStack bodyLayerStack;

	private AbstractDataProvider columnHeaderDataProvider;

	private AbstractDataProvider rowHeaderDataProvider;

	private BodyDataProvider bodyDataProvider;

	/**
	 * the sort model used for rows
	 */
	private IPapyrusSortModel rowSortModel;

	/**
	 * 
	 * Constructor.
	 * 
	 * @param table
	 *        the model of the table
	 */
	public AbstractNattableWidgetManager(final Table table) {
		this.table = table;
		tableContext = table.getContext();
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#createNattable(org.eclipse.swt.widgets.Composite, int,
	 *      org.eclipse.ui.IWorkbenchPartSite)
	 * 
	 * @param parent
	 * @param style
	 * @param site
	 * @return
	 */
	@Override
	public NatTable createNattable(final Composite parent, final int style, final IWorkbenchPartSite site) {
		bodyDataProvider = new BodyDataProvider(this);
		bodyLayerStack = new BodyLayerStack(bodyDataProvider, this);

		columnHeaderDataProvider = new ColumnHeaderDataProvider(this);
		columnHeaderLayerStack = new ColumnHeaderLayerStack(columnHeaderDataProvider, bodyLayerStack, bodyDataProvider, getRowSortModel());

		rowHeaderDataProvider = new RowHeaderDataProvider(this);


		rowHeaderLayerStack = new RowHeaderLayerStack(rowHeaderDataProvider, bodyLayerStack);


		final IDataProvider cornerDataProvider = new DefaultCornerDataProvider(columnHeaderDataProvider, rowHeaderDataProvider);
		final CornerLayer cornerLayer = new CornerLayer(new DataLayer(cornerDataProvider), rowHeaderLayerStack, columnHeaderLayerStack);
		cornerLayer.addConfiguration(new CornerConfiguration(this));
		gridLayer = new PapyrusGridLayer(bodyLayerStack, columnHeaderLayerStack, rowHeaderLayerStack, cornerLayer);
		gridLayer.addConfiguration(new DefaultPrintBindings());

		natTable = new NatTable(parent, gridLayer, false);



		natTable.addConfiguration(new PapyrusHeaderMenuConfiguration());

		natTable.addConfiguration(new CellEditionConfiguration());
		natTable.addConfiguration(new PapyrusClickSortConfiguration());


		configureNatTable();
		addColumnReorderListener(bodyLayerStack.getColumnReorderLayer());
		addDragAndDropSupport(natTable);


		if(site != null) {
			final MenuManager menuMgr = createMenuManager(natTable);
			final Menu menu = menuMgr.createContextMenu(natTable);
			natTable.setMenu(menu);

			selectionProvider = new TableSelectionProvider(this, bodyLayerStack.getSelectionLayer());
			site.registerContextMenu(menuMgr, selectionProvider);
			site.setSelectionProvider(selectionProvider);
		}
		new PapyrusNatTableToolTipProvider(natTable, GridRegion.BODY, GridRegion.COLUMN_HEADER, GridRegion.ROW_HEADER);
		return natTable;
	}

	protected void configureNatTable() {
		if(natTable != null && !natTable.isDisposed()) {
			ConfigRegistry configRegistry = new ConfigRegistry();
			configRegistry.registerConfigAttribute(NattableConfigAttributes.NATTABLE_MODEL_MANAGER_CONFIG_ATTRIBUTE, AbstractNattableWidgetManager.this, DisplayMode.NORMAL, NattableConfigAttributes.NATTABLE_MODEL_MANAGER_ID);
			configRegistry.registerConfigAttribute(NattableConfigAttributes.LABEL_PROVIDER_SERVICE_CONFIG_ATTRIBUTE, getLabelProviderService(), DisplayMode.NORMAL, NattableConfigAttributes.LABEL_PROVIDER_SERVICE_ID);
			//commented because seems generate several bugs with edition
			//newRegistry.registerConfigAttribute( CellConfigAttributes.DISPLAY_CONVERTER, new GenericDisplayConverter(), DisplayMode.NORMAL,  GridRegion.BODY);
			natTable.setConfigRegistry(configRegistry);
			natTable.setUiBindingRegistry(new UiBindingRegistry(natTable));
			natTable.configure();
		}
	}


	private LabelProviderService getLabelProviderService() {
		try {
			ServicesRegistry serviceRegistry = ServiceUtilsForEObject.getInstance().getServiceRegistry(table.getContext());//get context and NOT get table for the usecase where the table is not in a resource
			return serviceRegistry.getService(LabelProviderService.class);
		} catch (ServiceException e) {
			Activator.log.error(e);
		}
		return null;
	}

	/**
	 * 
	 * @param natTable
	 * @return
	 */
	public MenuManager createMenuManager(final NatTable natTable) {
		final MenuManager menuManager = new MenuManager("#PopUp", "org.eclipse.papyrus.infra.nattable.widget.menu") { //$NON-NLS-1$ //$NON-NLS-2$

			@Override
			public void add(final IAction action) {
				super.add(action);
			}

			@Override
			public void add(final IContributionItem item) {
				super.add(item);
			}
		};
		menuManager.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));

		menuManager.setRemoveAllWhenShown(true);
		return menuManager;
	}

	/**
	 * Enable the table to receive dropped elements
	 * 
	 * @param nattable
	 *        the nattable widget in which we add the drag&drop support
	 */
	protected void addDragAndDropSupport(final NatTable nattable) {
		final int operations = DND.DROP_MOVE | DND.DROP_COPY | DND.DROP_DEFAULT;
		final DropTarget target = new DropTarget(nattable, operations);
		final LocalTransfer localTransfer = LocalTransfer.getInstance();
		final Transfer[] types = new Transfer[]{ localTransfer };
		target.setTransfer(types);
		final NatTableDropListener dropListener = new NatTableDropListener(this);
		target.addDropListener(dropListener);
	}

	/**
	 * Add a listener on the column reorder layer in order to update the model
	 * 
	 * @param columnReorderLayer
	 *        the column reorder layer
	 */
	private void addColumnReorderListener(final ColumnReorderLayer columnReorderLayer) {
		columnReorderLayer.addLayerListener(new ILayerListener() {


			@Override
			public void handleLayerEvent(final ILayerEvent event) {
				if(event instanceof ColumnReorderEvent) {
					ColumnReorderEvent columnReorderEvent = (ColumnReorderEvent)event;
					int start = -1;
					int end = columnReorderEvent.getBeforeToColumnPosition();
					for(Range range : columnReorderEvent.getBeforeFromColumnPositionRanges()) {
						start = range.start;
						break;
					}

					if(start != -1) {
						final List<IAxis> allAxis = AbstractNattableWidgetManager.this.getColumnAxisManager().getRepresentedContentProvider().getAxis();

						// This solve an index difference between moving
						// a column from right to left and moving a
						// column from left to right
						if(start >= 0 && start < end && columnReorderEvent.isReorderToLeftEdge()) {
							end--;
						}

						final IAxis axisToMove = allAxis.get(start);
						if(axisToMove != null) {
							moveColumnElement(axisToMove, end);
						}
					}
				}
			}
		});
	}



	/**
	 * 
	 * @param event
	 *        an event
	 * @return
	 *         a LocationValue for the point, which contains informations about this location (TableGridRegion, row and column index, row and column
	 *         elements, the cell, the point and its translation).
	 *         Some of these values can be <code>null</code> or not depending of the region of the table
	 */
	@Override
	public LocationValue getLocationInTheTable(final Point absolutePoint) {
		final Point widgetPoint = natTable.toControl(absolutePoint.x, absolutePoint.y);
		TableGridRegion kind = TableGridRegion.UNKNOWN;
		int columnPosition = natTable.getColumnPositionByX(widgetPoint.x);
		int columnIndex = natTable.getColumnIndexByPosition(columnPosition);
		int rowPosition = natTable.getRowPositionByY(widgetPoint.y);
		int rowIndex = natTable.getRowIndexByPosition(rowPosition);
		final ILayerCell cell = natTable.getCellByPosition(columnPosition, rowPosition);
		Object columnObject = null;
		Object rowObject = null;
		if(rowIndex == -1 && columnIndex == -1) {
			kind = TableGridRegion.UNKNOWN;
		} else if(rowIndex == -1) {
			kind = TableGridRegion.AFTER_ROW_HEADER;
		} else if(columnIndex == -1) {
			kind = TableGridRegion.AFTER_COLUMN_HEADER;
		} else {
			if(cell != null) {
				LabelStack label = cell.getConfigLabels();
				if(label.hasLabel(GridRegion.ROW_HEADER)) {
					kind = TableGridRegion.ROW_HEADER;

				} else if(label.hasLabel(GridRegion.COLUMN_HEADER)) {
					kind = TableGridRegion.COLUMN_HEADER;

				} else if(label.hasLabel(GridRegion.CORNER)) {
					kind = TableGridRegion.CORNER;
				} else if(label.hasLabel(GridRegion.BODY)) {
					kind = TableGridRegion.CELL;
					columnObject = getColumnElement(columnIndex);
					rowObject = getRowElement(rowIndex);
				}
			}
		}
		return new LocationValue(absolutePoint, widgetPoint, kind, cell, columnIndex, rowIndex, columnObject, rowObject);
	}


	public GridLayer getGridLayer() {
		return gridLayer;
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#print()
	 * 
	 */
	@Override
	public void print() {
		natTable.doCommand(new TurnViewportOffCommand());
		natTable.doCommand(new PrintCommand(natTable.getConfigRegistry(), natTable.getShell()));
		natTable.doCommand(new TurnViewportOnCommand());
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#selectAll()
	 * 
	 */
	@Override
	public void selectAll() {
		natTable.doCommand(new SelectAllCommand());
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#exportToXLS()
	 * 
	 */
	@Override
	public void exportToXLS() {
		natTable.doCommand(new ExportCommand(natTable.getConfigRegistry(), natTable.getShell()));
	}

	public void copyToClipboard() {
		natTable.doCommand(new CopyDataToClipboardCommand("\t", "\n", natTable.getConfigRegistry()));
	}

	/**
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#getBodyLayerStack()
	 * 
	 * @return
	 */
	@Override
	public BodyLayerStack getBodyLayerStack() {
		return bodyLayerStack;
	}

	@Override
	public void dispose() {
		if(bodyDataProvider != null) {
			bodyDataProvider.dispose();
		}
		if(rowHeaderDataProvider != null) {
			rowHeaderDataProvider.dispose();
		}
		if(columnHeaderDataProvider != null) {
			columnHeaderDataProvider.dispose();
		}
		tableContext = null;
	}

	public EObject getTableContext() {
		return tableContext;
	}

	@Override
	public Table getTable() {
		return table;
	}

	/**
	 * 
	 * @return
	 *         the created sort model to use for
	 */
	protected IPapyrusSortModel getRowSortModel() {
		if(rowSortModel == null) {
			rowSortModel = new ColumnSortModel(this);
		}
		return rowSortModel;
	}

	/**
	 * 
	 * 
	 * Configuration used for cell edition in the table
	 */
	private class CellEditionConfiguration extends AbstractRegistryConfiguration {

		/**
		 * 
		 * @see org.eclipse.nebula.widgets.nattable.config.IConfiguration#configureRegistry(org.eclipse.nebula.widgets.nattable.config.IConfigRegistry)
		 * 
		 * @param configRegistry
		 */
		@Override
		public void configureRegistry(IConfigRegistry configRegistry) {
			configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITABLE_RULE, new EditableRule() {

				@Override
				public boolean isEditable(final int columnIndex, final int rowIndex) {
					final Object rowElement = getRowElement(rowIndex);
					final Object columnElement = getColumnElement(columnIndex);
					return CellManagerFactory.INSTANCE.isCellEditable(columnElement, rowElement);
				}
			});

			configRegistry.registerConfigAttribute(EditConfigAttributes.CELL_EDITOR, null, DisplayMode.EDIT, ""); //$NON-NLS-1$
		}
	}

	/**
	 * 
	 * handles the selections from the model explorer to the table when the link is activated
	 * 
	 * @see org.eclipse.papyrus.infra.nattable.manager.table.INattableModelManager#revealSemanticElement(java.util.List)
	 * @see org.eclipse.papyrus.infra.nattable.utils.AxisUtils
	 * @see org.eclipse.nebula.widgets.nattable.selection.SelectionLayer
	 * 
	 * @param elementList
	 */
	@Override
	public void revealSemanticElement(List<?> elementList) {
		SelectionLayer selectionLayer = bodyLayerStack.getSelectionLayer();
		List<Object> rowObjects = getRowElementsList();
		List<Object> columnObjects = getColumnElementsList();

		// clear the selectionLayer to avoid the previous selections to mess with the current
		selectionLayer.clear();

		for(int rowIndex = 0; rowIndex < rowObjects.size(); rowIndex++) {
			List<?> toFind = new ArrayList<Object>(elementList);
			Object currentAxisObject = rowObjects.get(rowIndex);
			Object currentRealObject = AxisUtils.getRepresentedElement(currentAxisObject);
			if(toFind.contains(currentRealObject)) {
				selectionLayer.doCommand(new SelectRowsCommand(selectionLayer, 0, rowIndex, false, true));
				//we remove the found object from the cloned elementList as they are already selected
				toFind.remove(currentRealObject);
			}
			if(toFind.isEmpty()) {
				// all objects are selected
				return;
			}
		}

		for(int columnIndex = 0; columnIndex < columnObjects.size(); columnIndex++) {
			List<?> toFind = new ArrayList<Object>(elementList);
			Object currentAxisObject = columnObjects.get(columnIndex);
			Object currentRealObject = AxisUtils.getRepresentedElement(currentAxisObject);
			if(toFind.contains(currentRealObject)) {
				selectionLayer.doCommand(new SelectColumnCommand(selectionLayer, columnIndex, 0, false, true));
				//we remove the found object from the cloned elementList as they are already selected
				toFind.remove(currentRealObject);
			}
			if(toFind.isEmpty()) {
				// all objects are selected
				return;
			}
		}
	}
}
