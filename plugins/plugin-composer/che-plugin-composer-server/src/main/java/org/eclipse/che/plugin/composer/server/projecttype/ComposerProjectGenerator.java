/**
 * ***************************************************************************** Copyright (c)
 * 2016-2017 Rogue Wave Software, Inc. All rights reserved. This program and the accompanying
 * materials are made available under the terms of the Eclipse Public License v1.0 which accompanies
 * this distribution, and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * <p>Contributors: Rogue Wave Software, Inc. - initial API and implementation
 * *****************************************************************************
 */
package org.eclipse.che.plugin.composer.server.projecttype;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.TimeoutException;
import javax.inject.Inject;
import org.eclipse.che.api.core.ConflictException;
import org.eclipse.che.api.core.ForbiddenException;
import org.eclipse.che.api.core.ServerException;
import org.eclipse.che.api.fs.server.PathTransformer;
import org.eclipse.che.api.project.server.handlers.CreateProjectHandler;
import org.eclipse.che.api.project.server.type.AttributeValue;
import org.eclipse.che.plugin.composer.server.executor.ComposerCommandExecutor;
import org.eclipse.che.plugin.composer.shared.Constants;

/** @author Kaloyan Raev */
public class ComposerProjectGenerator implements CreateProjectHandler {

  private final ComposerCommandExecutor commandExecutor;
  private final PathTransformer pathTransformer;

  @Inject
  public ComposerProjectGenerator(
      ComposerCommandExecutor commandExecutor, PathTransformer pathTransformer) {
    this.commandExecutor = commandExecutor;
    this.pathTransformer = pathTransformer;
  }

  @Override
  public void onCreateProject(
      String projectWsPath, Map<String, AttributeValue> attributes, Map<String, String> options)
      throws ForbiddenException, ConflictException, ServerException {
    AttributeValue packageName = attributes.get(Constants.PACKAGE);
    if (packageName == null) {
      throw new ServerException("Missed some required options (package)");
    }

    Path path = pathTransformer.transform(projectWsPath);
    String projectAbsolutePath = path.toString();
    String[] commandLine = {
      "composer", "create-project", packageName.getString(), projectAbsolutePath, "--no-install"
    };
    try {
      commandExecutor.execute(commandLine, null);
    } catch (TimeoutException | IOException | InterruptedException e) {
      throw new ServerException(e);
    }
  }

  @Override
  public String getProjectType() {
    return Constants.COMPOSER_PROJECT_TYPE_ID;
  }
}
