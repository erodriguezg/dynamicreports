/**
 * DynamicReports - Free Java reporting library for creating reports dynamically
 *
 * Copyright (C) 2010 - 2011 Ricardo Mariaca
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

package net.sf.dynamicreports.report.builder.group;

import net.sf.dynamicreports.report.builder.FieldBuilder;
import net.sf.dynamicreports.report.builder.expression.Expressions;
import net.sf.dynamicreports.report.constant.Constants;
import net.sf.dynamicreports.report.constant.GroupHeaderLayout;
import net.sf.dynamicreports.report.definition.expression.DRIComplexExpression;
import net.sf.dynamicreports.report.definition.expression.DRISimpleExpression;

import org.apache.commons.lang.Validate;

/**
 * @author Ricardo Mariaca (dynamicreports@gmail.com)
 */
@SuppressWarnings("ucd")
public class CustomGroupBuilder extends GroupBuilder<CustomGroupBuilder> {	
	private static final long serialVersionUID = Constants.SERIAL_VERSION_UID;
	
	protected CustomGroupBuilder(FieldBuilder<?> field) {
		Validate.notNull(field, "field must not be null");
		setValueExpression(field.build());
	}	

	protected CustomGroupBuilder(String name, FieldBuilder<?> field) {
		super(name);
		Validate.notNull(field, "field must not be null");
		setValueExpression(field.build());
	}	
	
	protected CustomGroupBuilder(DRISimpleExpression<?> valueExpression) {
		setValueExpression(valueExpression);
	}

	protected CustomGroupBuilder(String name, DRISimpleExpression<?> valueExpression) {
		super(name);
		setValueExpression(valueExpression);
	}
	
	public CustomGroupBuilder setTitle(DRISimpleExpression<?> titleExpression) {
		getObject().setTitleExpression(titleExpression);
		getObject().setHeaderLayout(GroupHeaderLayout.TITLE_AND_VALUE);
		return this;
	}

	public CustomGroupBuilder setTitle(DRIComplexExpression<?> titleExpression) {
		getObject().setTitleExpression(titleExpression);
		getObject().setHeaderLayout(GroupHeaderLayout.TITLE_AND_VALUE);
		return this;
	}

	public CustomGroupBuilder setTitle(String title) {
		getObject().setTitleExpression(Expressions.text(title));
		getObject().setHeaderLayout(GroupHeaderLayout.TITLE_AND_VALUE);
		return this;
	}
}
