package com.jadecross.operator.guestbook;

import com.jadecross.operator.guestbook.customresources.Guestbook;
import com.jadecross.operator.guestbook.dependentresources.GuestbookConfigMap;
import com.jadecross.operator.guestbook.dependentresources.GuestbookDeployment;
import com.jadecross.operator.guestbook.dependentresources.GuestbookService;

import io.javaoperatorsdk.operator.api.reconciler.Cleaner;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.DeleteControl;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusHandler;
import io.javaoperatorsdk.operator.api.reconciler.ErrorStatusUpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;
import io.javaoperatorsdk.operator.api.reconciler.dependent.Dependent;

@ControllerConfiguration(dependents = { @Dependent(type = GuestbookDeployment.class),
		@Dependent(type = GuestbookService.class), @Dependent(type = GuestbookConfigMap.class) })
public class GuestbookReconciler implements Reconciler<Guestbook>, ErrorStatusHandler<Guestbook>, Cleaner<Guestbook> {

	@Override
	public UpdateControl<Guestbook> reconcile(Guestbook guestbook, Context<Guestbook> context) {
		return UpdateControl.updateResourceAndPatchStatus(guestbook);
	}

	@Override
	public DeleteControl cleanup(Guestbook guestbook, Context<Guestbook> context) {
		return DeleteControl.defaultDelete();
	}

	@Override
	public ErrorStatusUpdateControl<Guestbook> updateErrorStatus(Guestbook guestbook, Context<Guestbook> context,
			Exception e) {
		return ErrorStatusUpdateControl.patchStatus(guestbook);
	}

}