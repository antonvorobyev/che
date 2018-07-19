/*
 * Copyright (c) 2012-2018 Red Hat, Inc.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *   Red Hat, Inc. - initial API and implementation
 */
package org.eclipse.che.api.project.shared.dto.service;

import java.util.List;
import org.eclipse.che.api.project.shared.dto.NewProjectConfigDto;
import org.eclipse.che.dto.shared.DTO;

@DTO
public interface CreateBatchProjectsRequestDto {
  boolean isRewrite();

  void setRewrite(boolean rewrite);

  CreateBatchProjectsRequestDto withRewrite(boolean rewrite);

  List<NewProjectConfigDto> getNewProjectConfigs();

  void setNewProjectConfigs(List<NewProjectConfigDto> newProjectConfigs);

  CreateBatchProjectsRequestDto withNewProjectConfigs(List<NewProjectConfigDto> newProjectConfigs);
}
