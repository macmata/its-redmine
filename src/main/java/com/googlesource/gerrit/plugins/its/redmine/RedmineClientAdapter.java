// Copyright (C) 2015 The Android Open Source Project
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.
package com.googlesource.gerrit.plugins.its.redmine;

import com.taskadapter.redmineapi.*;
import com.taskadapter.redmineapi.bean.Issue;

import com.taskadapter.redmineapi.bean.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.transaction.InvalidTransactionException;

public class RedmineClientAdapter {
  private static final Logger log = LoggerFactory.getLogger(RedmineClientAdapter.class);

  private RedmineManager mgr;

  public RedmineClientAdapter(String url, String apiKey) {
    this.mgr = RedmineManagerFactory.createWithApiKey(url, apiKey);
  }

  /**
   * Add a note on a existing issue in Redmine
   *
   * @param strIssueId a Redmine issue id string
   * @param comment    a generated comment from the information in Gerrit
   */
  public void addNotesIssue(String strIssueId, String comment) throws
      InvalidTransactionException, NumberFormatException {
    IssueManager issueManager = this.mgr.getIssueManager();
    int issueId = Integer.parseInt(strIssueId);
    try {
      Issue issue = issueManager.getIssueById(issueId, Include.watchers);
      issue.setNotes(comment);
      issueManager.update(issue);
    } catch (RedmineException e) {
      throw new InvalidTransactionException();
    }
  }

  public String accessHealthCheck() {
    try {
      List <User> userList = this.mgr.getUserManager().getUsers();
      // By default admin is present at installation therefore we know than min size should be 1
      if (userList.size() > 0 ) {
        return "{\"status\"=\"ok\"}";
      } else {
        return "{\"status\"=\"error\"}";
      }
    } catch (RedmineException e) {
      return "{\"status\"=\"error\"}";
    }
  }

  public String sysHealthCheck() {
    return "{\"status\"=\"not implemented\"}";
  }

  public boolean exits(String strIssueId) throws IllegalArgumentException {
    IssueManager issueManager = this.mgr.getIssueManager();
    try {
      int issueId = Integer.parseInt(strIssueId);
      Issue issue = issueManager.getIssueById(issueId, Include.watchers);
      return issue.getId() != null && issue.getId() == issueId;
    } catch (RedmineException e) {
      throw new IllegalArgumentException();
    }
  }
}
