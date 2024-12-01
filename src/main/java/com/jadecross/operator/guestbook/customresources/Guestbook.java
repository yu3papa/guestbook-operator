package com.jadecross.operator.guestbook.customresources;

import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

@Group("com.jadecross")
@Version("v1")
public class Guestbook extends CustomResource<GuestbookSpec, GuestbookStatus> implements Namespaced {

	@Override
	public String toString() {
		return "Guestbook{spec=" + spec + ", status=" + status + "}";
	}
}