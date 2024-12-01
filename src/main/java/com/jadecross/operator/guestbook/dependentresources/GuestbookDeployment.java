package com.jadecross.operator.guestbook.dependentresources;

import com.jadecross.operator.guestbook.customresources.Guestbook;

import io.fabric8.kubernetes.api.model.ContainerBuilder;
import io.fabric8.kubernetes.api.model.ContainerPortBuilder;
import io.fabric8.kubernetes.api.model.LabelSelectorBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.PodTemplateSpecBuilder;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

@KubernetesDependent
public class GuestbookDeployment extends CRUDKubernetesDependentResource<Deployment, Guestbook> {

	public GuestbookDeployment() {
		super(Deployment.class);
	}

	@Override
	protected Deployment desired(Guestbook guestbook, Context<Guestbook> context) {
		final ObjectMeta guestbookMetadata = guestbook.getMetadata();
		final String guestbookName = guestbookMetadata.getName();

		return new DeploymentBuilder().editMetadata().withName(guestbookName)
				.withNamespace(guestbookMetadata.getNamespace()).addToLabels("app", guestbookName).endMetadata()
				.editSpec().withSelector(new LabelSelectorBuilder().addToMatchLabels("app", guestbookName).build())
				.withReplicas(guestbook.getSpec().size)
				.withTemplate(
						new PodTemplateSpecBuilder().editMetadata().addToLabels("app", guestbookName).endMetadata()
								.editSpec()
								.withContainers(new ContainerBuilder().withName(guestbookName + "-container")
										.withImage(guestbook.getSpec().image).addNewEnvFrom()
										.withNewConfigMapRef(guestbookName + "-cm", false).endEnvFrom()
										.addToPorts(new ContainerPortBuilder()
												.withContainerPort(guestbook.getSpec().port).build())
										.build())
								.endSpec().build())
				.endSpec().build();
	}
}