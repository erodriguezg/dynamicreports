/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2012 Ricardo Mariaca
 * http://dynamicreports.sourceforge.net
 *
 * This file is part of DynamicReports.
 *
 * DynamicReports is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * DynamicReports is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with DynamicReports. If not, see <http://www.gnu.org/licenses/>.
 */

package net.sf.dynamicreports.design.transformation;

import java.util.ArrayList;
import java.util.List;

import net.sf.dynamicreports.design.base.DRDesignParameter;
import net.sf.dynamicreports.design.base.DRDesignQuery;
import net.sf.dynamicreports.design.base.DRDesignTemplateDesign;
import net.sf.dynamicreports.design.definition.DRIDesignParameter;
import net.sf.dynamicreports.design.definition.DRIDesignTemplateDesign;
import net.sf.dynamicreports.design.definition.expression.DRIDesignExpression;
import net.sf.dynamicreports.jasper.base.JasperScriptlet;
import net.sf.dynamicreports.report.definition.DRIParameter;
import net.sf.dynamicreports.report.definition.DRIQuery;
import net.sf.dynamicreports.report.definition.DRIReport;
import net.sf.dynamicreports.report.definition.expression.DRIExpression;
import net.sf.dynamicreports.report.exception.DRException;

/**
 * @author Ricardo Mariaca (dynamicreports@gmail.com)
 */
public class ReportTransform {
	private DesignTransformAccessor accessor;
	private List<DRIDesignExpression> templates;
	private DRIDesignTemplateDesign templateDesign;
	private DRDesignQuery query;
	private List<DRIDesignParameter> parameters;
	private DRIDesignExpression filterExpression;

	public ReportTransform(DesignTransformAccessor accessor) {
		this.accessor = accessor;
		parameters = new ArrayList<DRIDesignParameter>();
	}

	public void transform() throws DRException {
		DRIReport report = accessor.getReport();
		templates = new ArrayList<DRIDesignExpression>();
		for (DRIExpression<?> template : report.getTemplates()) {
			templates.add(accessor.getExpressionTransform().transformExpression(template));
		}
		templateDesign = new DRDesignTemplateDesign(report.getTemplateDesign());
		if (report.getQuery() != null) {
			query = query(report.getQuery());
		}
		for (DRIParameter<?> parameter : report.getParameters()) {
			parameters.add(parameter(parameter));
		}
		filterExpression = accessor.getExpressionTransform().transformExpression(report.getFilterExpression(), JasperScriptlet.SCRIPTLET_NAME);
	}

	protected DRDesignQuery query(DRIQuery query) {
		DRDesignQuery designQuery = new DRDesignQuery();
		designQuery.setText(query.getText());
		designQuery.setLanguage(query.getLanguage());
		return designQuery;
	}

	private DRDesignParameter parameter(DRIParameter<?> parameter) {
		DRDesignParameter designParameter = new DRDesignParameter();
		designParameter.setName(parameter.getName());
		designParameter.setValueClass(parameter.getValueClass());
		designParameter.setValue(parameter.getValue());
		designParameter.setExternal(accessor.getReport().getTemplateDesign().isDefinedParameter(parameter.getName()));
		return designParameter;
	}

	public List<DRIDesignExpression> getTemplates() {
		return templates;
	}

	public DRIDesignTemplateDesign getTemplateDesign() {
		return templateDesign;
	}

	public DRDesignQuery getQuery() {
		return query;
	}

	public List<DRIDesignParameter> getParameters() {
		return parameters;
	}

	public DRIDesignExpression getFilterExpression() {
		return filterExpression;
	}
}
