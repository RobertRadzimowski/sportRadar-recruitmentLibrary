# SportRadar-RecruitmentLibrary
As the name suggests, this is a very simple library written for recruitment purposes.

## MVP
It's easy to overengineer or oversimplify things.

I tried to stick to rather basic, widely used corporate IT standards of descriptive naming policy, with javadocs explaining contracts and assumptions. No comments describing what's happening as they are usually indicators for rework needs. This code should be easy to read and understand even for juniorDev.

For tests, I used typical GWT composition and a scenario/case-based approach, which I vastly prefer over a specific method per scenario - less writing, fewer errors, and more cases covered. win-win-win.

There is room for more test cases and scenarios, but as stated in the paragraph title - this is MVP. It's viable to be used, with room for improvements. I also didn't want to overcommit with time for it -after all, keeping it simple was a requirement :)

##Assumptions
1. To simplify the code I added ID to match, derived from teams' names and ordering. It could be omitted, as there shouldn't be a case that the same team appears in 2 matches at the same time.
2. Because of 1. I used map to hold active games, casting it to ordered list only when necessary. This is a typical case of choosing what we want to optimize - by going with map, adding/removing is simplified, but getting list is more time consuming (not important for this particular case size) and saves time on dev side.
3. Added not-mentioned but logical requirements eg. no negative game scores (throws an exception),  and the finishing match was made safe for wrong arguments. I'm mentioning those 2 on purpose. Why? A different approach is caused by differences in expected results: wrong score can't be used (illegalState), but removing non-existing data, leads to no action and no data corrupted (success of operation determined by returned object).
4. Both Match and Scoreboard could be implementations of interfaces, but that would lead to more boilerplate and for this particular case is overengineering.
5. I changed ID creation method because I observed rare creation race collision which led to nondeterministic behavior for comparing draw games.

   
