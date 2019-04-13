package com.semantics.thrillio;

import com.semantics.thrillio.constants.KidFriendlyStatus;
import com.semantics.thrillio.constants.UserType;
import com.semantics.thrillio.controller.BookmarkController;
import com.semantics.thrillio.entities.Bookmark;
import com.semantics.thrillio.entities.User;
import com.semantics.thrillio.partner.Shareable;

public class View {

	public static void browse(User user, Bookmark[][] bookmarks) {
		System.out.println("\n" + user.getEmail() + " is browsing items..");
		int bookmarkCount = 0;

		for (Bookmark[] bookmarkList : bookmarks) {
			for (Bookmark bookmark : bookmarkList) {

				if (bookmarkCount < DataStore.USER_BOOKMARK_LIMIT) {
					boolean isBookmarked = getBookmarkDecision(bookmark);
					if (isBookmarked) {
						bookmarkCount++;
						BookmarkController.getInstance().saveUserBookmark(user, bookmark);
						System.out.println("New item bookmarked : " + bookmark);
					}
				}

				if (user.getUserType().equals(UserType.CHIEF_EDITOR) || user.getUserType().equals(UserType.EDITOR)) {

					// Mark as kid-friendly
					if (bookmark.isKidFriendlyEligible()
							&& bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.UNKNOWN)) {
						String kidFriendlyStatus = getKidFriendlyDecision(bookmark);
						if (!kidFriendlyStatus.equals(KidFriendlyStatus.UNKNOWN)) {
							BookmarkController.getInstance().setKidFriendlyStatus(user, kidFriendlyStatus, bookmark);
						}
					}

					// Sharing !!
					if (bookmark.getKidFriendlyStatus().equals(KidFriendlyStatus.APPROVED)
							&& bookmark instanceof Shareable) {
						boolean isShared = getShareDecision();
						if(isShared) {
							BookmarkController.getInstance().share(user, bookmark);
						}
					}
				}
			}
		}
	}
	
	// TODO: Below methods simulate user input. After IO, we take input via console
	private static boolean getShareDecision() {
		return Math.random() < 0.5 ? true : false;
	}

	private static String getKidFriendlyDecision(Bookmark bookmark) {
		return Math.random() < 0.4 ? KidFriendlyStatus.APPROVED
				: (Math.random() > 0.5 && Math.random() < 0.8) ? KidFriendlyStatus.REJECTED : KidFriendlyStatus.UNKNOWN;
	}

	private static boolean getBookmarkDecision(Bookmark bookmark) {
		return Math.random() < 0.5 ? true : false;
	}
}
