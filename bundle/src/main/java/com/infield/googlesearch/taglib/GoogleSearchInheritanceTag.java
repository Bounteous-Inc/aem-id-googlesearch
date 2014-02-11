/*************************************************************************
 * Copyright 2014 Infield Design
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * @author Gaetan Marmasse <gaetan@infielddesign.com>
 * 
 *************************************************************************/

package com.infield.googlesearch.taglib;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceUtil;
import org.apache.sling.api.resource.ValueMap;
import java.io.IOException;
import javax.servlet.jsp.JspException;
import com.day.cq.commons.inherit.HierarchyNodeInheritanceValueMap;
import com.day.cq.commons.inherit.InheritanceValueMap;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class GoogleSearchInheritanceTag extends SimpleTagSupport {

	private Resource resource = null;
	private String propertyName = null;
	private String defaultValue = "";
	
	@Override
	public void doTag() throws JspException, IOException {
		
		// get property of current page or traverse up tree:
		String inheritedPropertyValue = null;
		if(!ResourceUtil.isSyntheticResource(this.resource)) {
			inheritedPropertyValue = this.resource.adaptTo(ValueMap.class).get(this.propertyName, "").toString();	
		}
		if (inheritedPropertyValue == null || inheritedPropertyValue.length() == 0) {
			InheritanceValueMap iProperties = new HierarchyNodeInheritanceValueMap(this.resource);
			inheritedPropertyValue = iProperties.getInherited(this.propertyName, this.defaultValue);
		}
		
		getJspContext().setAttribute("inheritedPropertyValue", inheritedPropertyValue);
		
	}

	/**
	 * @return the resource
	 */
	public Resource getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	/**
	 * @return the propertyName
	 */
	public String getPropertyName() {
		return propertyName;
	}

	/**
	 * @param propertyName the propertyName to set
	 */
	
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	/**
	 * @return the defaultValue
	 */
	public String getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	
	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}
	
}