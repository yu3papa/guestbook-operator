package com.jadecross.operator.guestbook.dependentresources;

import com.jadecross.operator.guestbook.customresources.Guestbook;

import io.fabric8.kubernetes.api.model.IntOrStringBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.ServiceBuilder;
import io.fabric8.kubernetes.api.model.ServicePortBuilder;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

@KubernetesDependent
public class GuestbookService extends CRUDKubernetesDependentResource<Service, Guestbook> {

	public GuestbookService() {
		super(Service.class);
	}

	@Override
	protected Service desired(Guestbook guestbook, Context<Guestbook> context) {
		final ObjectMeta guestbookMetadata = guestbook.getMetadata();
		final String guestbookName = guestbookMetadata.getName();

		return new ServiceBuilder().editMetadata().withName(guestbookName + "-svc")
				.withNamespace(guestbookMetadata.getNamespace()).addToLabels("app", guestbookName).endMetadata()
				.editSpec().withType("NodePort").addToSelector("app", guestbookName)
				.addToPorts(new ServicePortBuilder().withName("http").withPort(guestbook.getSpec().port)
						.withProtocol("TCP")
						.withTargetPort(new IntOrStringBuilder().withValue(guestbook.getSpec().port).build()).build())
				.endSpec().build();
	}
}