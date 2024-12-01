package com.jadecross.operator.guestbook.dependentresources;

import java.util.HashMap;
import java.util.Map;

import com.jadecross.operator.guestbook.customresources.Guestbook;

import io.fabric8.kubernetes.api.model.ConfigMap;
import io.fabric8.kubernetes.api.model.ConfigMapBuilder;
import io.fabric8.kubernetes.api.model.ObjectMeta;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.CRUDKubernetesDependentResource;
import io.javaoperatorsdk.operator.processing.dependent.kubernetes.KubernetesDependent;

@KubernetesDependent
public class GuestbookConfigMap extends CRUDKubernetesDependentResource<ConfigMap, Guestbook> {

	public GuestbookConfigMap() {
		super(ConfigMap.class);
	}

	@Override
	protected ConfigMap desired(Guestbook guestbook, Context<Guestbook> context) {
		final ObjectMeta guestbookMetadata = guestbook.getMetadata();
		final String guestbookName = guestbookMetadata.getName();

		Map<String, String> dataMap = new HashMap<String, String>();
		dataMap.put("MYSQL_ADDR", guestbook.getSpec().db.host);
		dataMap.put("MYSQL_PORT", guestbook.getSpec().db.port + "");
		dataMap.put("MYSQL_DATABASE", guestbook.getSpec().db.database);
		dataMap.put("MYSQL_USER", guestbook.getSpec().db.userid);
		dataMap.put("MYSQL_USER_PASSWORD", guestbook.getSpec().db.password);

		return new ConfigMapBuilder().editMetadata().withName(guestbookName + "-cm")
				.withNamespace(guestbookMetadata.getNamespace()).addToLabels("app", guestbookName).endMetadata()
				.addToData(dataMap).build();
	}
}