CIPHER Subscription Tiers Analysis (Phase 1-7)
Based on a comprehensive review of the project’s feature set across Phases 1 through 7, including the billing data model (
SubscriptionTier.kt
), here is the breakdown of features designated for Free vs. Pro vs. Ultra (Power) users, along with their current implementation status in the codebase.

🟢 FREE Tier (Ad-Supported)
Designed for basic users who just need a sleek media player and a functional, small vault.

Features Included:

Polished Media Player: Core ExoPlayer video and audio playback (Staggered animations, glassmorphism, Picture-in-Picture).
Basic Security Vault: Up to 1GB of encrypted storage.
Intruder Detection: Core stealth capture (Selfie + GPS location logged on wrong PIN).
Basic Equalizer: Access to 3 customizable EQ bands.
Video Quality: Playback capped at 720p.
Ads Enabled: Interstitial AdMob ads trigger every 5th video played.
Current Implementation Status:

✅ Player, Vault Encrpytion, CameraX Selfie, Interstitial Ad triggers are fully built and working.
❌ 1GB Storage Limit & 720p Quality Cap are NOT enforced yet. Free users currently get unlimited storage and original quality video.
❌ 3-Band EQ Limit is NOT enforced yet. Free users currently see all 10 bands in the 
EqualizerScreen
.
🔵 PRO Tier (₹99/mo or ₹499/yr)
Designed for power users who want an ad-free experience, a serious privacy vault, and high-quality audio.

Features Included:

Ad-Free Experience: All AdMob interstitials are completely removed.
Expanded Vault: Up to 10GB of encrypted storage.
Pro Audio DSP: Full access to 10 EQ bands, Bass Boost, and Virtualizer.
Video Quality: Playback supported up to 1080p FHD.
Basic Cloud Sync: Ability to back up the encrypted vault to a linked cloud account.
Advanced Animations: Premium UI interactions (Glassmorphic cards, Neon glow borders, Spring physics).
Current Implementation Status:

✅ Ad-Removal Logic is partially wired in 
CIPHERNavigation
 (skips ads if purchased, but needs BillingClient verification).
✅ 10-Band EQ & Advanced DSP are fully built via 
AudioEffectsManager
 (but currently free users can access them too).
✅ Advanced Animations were just implemented across the app.
❌ Vault Storage Limits & 1080p Quality Caps are NOT enforced.
❌ Cloud Sync is NOT built yet.
🟣 ULTRA (Power) Tier (₹149/mo or ₹999/yr)
Designed for audiophiles, videophiles, and extreme privacy advocates relying entirely on CIPHER.

Features Included:

Unlimited Vault Storage: No limits on the encrypted size.
4K HDR Video Playback: Uncapped resolution and High Dynamic Range support.
Decoy Vault & Disguised Icon: Exclusive extreme privacy features (Fake PIN, Calculator disguise).
Real-Time Intruder Email Alerts: Instant notification to a secure email when a break-in attempt occurs.
Family Sharing: Support for up to 5 family members.
Current Implementation Status:

✅ Calculator Disguise is built via 
StealthViewModel
 and PackageManager alias manipulation.
❌ Unlimited Vault / 4K Enforcements are not actively restricted for lower tiers yet.
❌ Decoy Vault is NOT built yet (Proposed).
❌ Intruder Email Alerts are NOT built yet (Proposed).
❌ Family Sharing is handled by Google Play Billing but logic to verify shared entitlement is missing.
☁️ Backend Architecture Plan: Firebase & Cloudinary
To support the premium tiers and accurately track revenue, we need to introduce a backend infrastructure. We will rely on free-tier friendly services to minimize costs.

1. Subscription & Payment Tracker (Firebase Firestore)
When a user completes a payment for the Pro or Ultra tier (via Ad Removal or Subscription), we need to track this to understand our paying user base and verify purchases.

Flow:

Upon successful Razorpay transaction in 
BillingManager.kt
, prompt the user with a "Thank You" dialogue.
The dialogue will collect:
Name
Email
Payment ID (Razorpay transaction ID)
Subscription Tier
This data will be securely pushed to Firebase Firestore into a premium_subscribers collection.
Benefit: We can track how many people paid, reconcile it with Razorpay payouts, and quickly restore purchases using their Email/ID if they lose their device.
2. Zero-Knowledge Cloud Sync (Cloudinary)
The Ultra tier promises Cloud Sync for the Encrypted Vault. Storing video/audio in the cloud gets expensive quickly.

Flow:

We will use Cloudinary (Free Tier) as our image/video object repository.
Since the Vault encrypts files locally via AES-256 BEFORE saving them to disk, we will upload the already-encrypted .enc files to Cloudinary.
We will map the local database metadata (file name, size, Cloudinary URL) and back up the SQLite mappings to Firebase.
Benefit: Cloudinary handles the heavy hosting for free (generous media limits). Because the files are encrypted locally first (Zero-Knowledge), even if someone accesses the Cloudinary bucket, they cannot view the users' photos/videos.
📋 Summary of What Needs Implementation
To truly enforce these tiers and give users a reason to pay, the following Business Logic Enforcement tasks must be completed:

Storage Limiter: Update 
VaultViewModel
 to reject files if the Free user's encrypted database surpasses 1GB or the 
Pro
 user surpasses 10GB.
EQ Band Limiter: Update 
EqualizerScreen
 to gray out or lock sliders 4-10 unless the user is on the 
Pro
 or Ultra tier.
Ad Verification: Ensure 
AdManager
 checks BillingManager.isPurchased(PRO_TIER) before displaying the interstitial every 5 videos.
Stealth Mode Lock: Disable the "Calculator Alias / Stealth Mode" toggle in 
SettingsScreen
 for Free users.
Build Proposed Premium Features: Actually build the Decoy Vault, Cloud Sync, and Email Alerts so Ultra users get what they pay for.