//snippet-sourcedescription:[ListTaskDefinitions.java demonstrates how to list task definitions.]
//snippet-keyword:[AWS SDK for Java v2]
//snippet-keyword:[Code Sample]
//snippet-service:[Amazon Elastic Container Service]
//snippet-sourcetype:[full-example]
//snippet-sourcedate:[06/20/2021]
//snippet-sourceauthor:[scmacdon-aws]

/*
   Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved.
   SPDX-License-Identifier: Apache-2.0
*/
package com.example.ecs;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ecs.EcsClient;
import software.amazon.awssdk.services.ecs.model.DescribeTasksRequest;
import software.amazon.awssdk.services.ecs.model.DescribeTasksResponse;
import software.amazon.awssdk.services.ecs.model.EcsException;
import software.amazon.awssdk.services.ecs.model.Task;
import java.util.List;

/**
 To run this Java V2 code example, ensure that you have setup your development environment,
 including your credentials.

 For information, see this documentation topic:
 https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/get-started.html
 */
public class ListTaskDefinitions {

    public static void main(String[] args) {

        final String usage = "\n" +
                "Usage:\n" +
                "  ListTaskDefinitions " +
                "   <clusterArn> <taskId> \n\n" +
                "Where:\n" +
                "  clusterArn - the ARN of an ECS cluster.\n" +
                "  taskId - the task Id value.\n" ;

        if (args.length != 2) {
            System.out.println(usage);
            System.exit(1);
        }

        String clusterArn = args[0];
        String taskId = args[1];
        Region region = Region.US_EAST_1;
        EcsClient ecsClient = EcsClient.builder()
                .region(region)
                .build();

        getAllTasks(ecsClient, clusterArn, taskId);
        ecsClient.close();
    }

    public static void getAllTasks(EcsClient ecsClient,  String clusterArn, String taskId) {


        try {
            DescribeTasksRequest tasksRequest = DescribeTasksRequest.builder()
                .cluster(clusterArn)
                .tasks(taskId)
                .build();

            DescribeTasksResponse response = ecsClient.describeTasks(tasksRequest);
            List<Task> tasks = response.tasks();
            for (Task task: tasks) {
                System.out.println("The task ARN is "+task.taskDefinitionArn());
            }

        } catch (EcsException e) {
            System.err.println(e.awsErrorDetails().errorMessage());
            System.exit(1);
        }
    }
}
