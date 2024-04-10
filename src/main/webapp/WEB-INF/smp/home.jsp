<%@ include file="/WEB-INF/smp/top.jsp"%>
<style>
    /* MARKETING CONTENT
-------------------------------------------------- */

    /* Center align the text within the three columns below the carousel */
    .marketing .col-lg-4 {
        margin-bottom: 1.5rem;
        text-align: center;
    }
    /* rtl:begin:ignore */
    .marketing .col-lg-4 p {
        margin-right: .75rem;
        margin-left: .75rem;
    }
    /* rtl:end:ignore */


    /* Featurettes
    ------------------------- */

    .featurette-divider {
        margin: 5rem 0; /* Space out the Bootstrap <hr> more */
    }

    /* Thin out the marketing headings */
    /* rtl:begin:remove */
    .featurette-heading {
        letter-spacing: -.05rem;
    }

</style>
<main style="margin-top: 75px; margin-bottom: 40px;">

    <!-- Flash Message -->
    <%@ include file="/WEB-INF/smp/flash-message.jsp"%>

    <div id="myCarousel" class="carousel slide mb-4" data-bs-ride="carousel" style="height: 60vh">
        <div class="carousel-indicators">
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="0" class="active" aria-current="true" aria-label="Slide 1"></button>
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="1" aria-label="Slide 2"></button>
            <button type="button" data-bs-target="#myCarousel" data-bs-slide-to="2" aria-label="Slide 3"></button>
        </div>
        <div class="carousel-inner h-100">
            <div class="carousel-item h-100 active">
                <img class="object-fit-cover" style="object-position: center;" src="${appURL}/images/smp/homepage/2024-04-10_12.27.26.png" width="100%" height="100%"/>
                <div class="container">
                    <div class="carousel-caption text-start">
                        <h1>Welcome to the No Go Outside server website!</h1>
                        <p class="opacity-75">This website was built for members of the No Go Outside server to have a central place to come and post about builds they made, hold votes, open discussion forms, and read up on the server's history.</p>
                        <p><a class="btn btn-lg btn-primary" href="${appURL}/smp-signup">Sign up today</a></p>
                    </div>
                </div>
            </div>
            <div class="carousel-item h-100">
                <img class="object-fit-cover" style="object-position: center;" src="${appURL}/images/smp/homepage/image.png" width="100%" height="100%"/>
                <div class="container">
                    <div class="carousel-caption">
                        <h1>View and post server builds!</h1>
                        <p>On this website, you are able to view and post builds from the server. When creating a build, give it a title, description, and upload an image(additional info is optional) and post it for everyone to see.</p>
                        <p><a class="btn btn-lg btn-primary" href="${appURL}/server-builds">Browse builds</a></p>
                    </div>
                </div>
            </div>
            <div class="carousel-item h-100">
                <img class="object-fit-cover" style="object-position: center;" src="${appURL}/images/smp/homepage/court-room.png" width="100%" height="100%"/>
                <div class="container">
                    <div class="carousel-caption text-end">
                        <h1>Hold server votes right here</h1>
                        <p>As a member of this website, you can create, hold, and vote for votes to decide a new world seed or new president.</p>
                        <p><a class="btn btn-lg btn-primary" href="${appURL}/votes">Browse votes</a></p>
                    </div>
                </div>
            </div>
        </div>
        <button class="carousel-control-prev" type="button" data-bs-target="#myCarousel" data-bs-slide="prev">
            <span class="carousel-control-prev-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Previous</span>
        </button>
        <button class="carousel-control-next" type="button" data-bs-target="#myCarousel" data-bs-slide="next">
            <span class="carousel-control-next-icon" aria-hidden="true"></span>
            <span class="visually-hidden">Next</span>
        </button>
    </div>


    <!-- Marketing messaging and featurettes
    ================================================== -->
    <!-- Wrap the rest of the page in another container to center all the content. -->

    <div class="container marketing">

        <div class="container p-0">
            <h2>Server Members</h2>
            <!-- Three columns of text below the carousel -->
            <div class="row">
                <div class="col-lg-4">
                    <img class="rounded-circle" src="${appURL}/images/smp/player-heads/JayrodHarv212.png" width="140" height="140"/>
                    <h3 class="fw-normal">JayrodHarv212</h3>
                    <p>Server admin and this website's creator. Likes to build redstone contraptions and mob farms.</p>
    <%--                <p><a class="btn btn-secondary" href="#">View details &raquo;</a></p>--%>
                </div><!-- /.col-lg-4 -->
                <div class="col-lg-4">
                    <img class="rounded-circle" src="${appURL}/images/smp/player-heads/BonHat.png" width="140" height="140"/>
                    <h3 class="fw-normal">BonHat</h3>
                    <p>BonHat is one of the original players and is known for her capitol buildings.</p>
                </div><!-- /.col-lg-4 -->
                <div class="col-lg-4">
                    <img class="rounded-circle" src="${appURL}/images/smp/player-heads/HyacinthFlower18.png" width="140" height="140"/>
                    <h3 class="fw-normal">HyacinthFlower18</h3>
                    <p>HyacinthFlower18 is known for his cathedrals and roman builds. One of the greatest builders on the server.</p>
                </div><!-- /.col-lg-4 -->
                <div class="col-lg-4">
                    <img class="rounded-circle" src="${appURL}/images/smp/player-heads/SisterComet.png" width="140" height="140"/>
                    <h3 class="fw-normal">SisterComet</h3>
                    <p>Started when server was on bedrock. Best known for her attention-to-detail when it comes to interiors and the giant castle built by her and HyacinthFlower18.</p>
                </div>
                <div class="col-lg-4">
                    <img class="rounded-circle" src="${appURL}/images/smp/player-heads/Ag3ntLan3z.png" width="140" height="140"/>
                    <h3 class="fw-normal">Ag3ntLan3z</h3>
                    <p>Wacky dude.</p>
                </div>
                <div class="col-lg-4">
                    <img class="rounded-circle" src="${appURL}/images/smp/player-heads/SoulDragonVoid.png" width="140" height="140"/>
                    <h3 class="fw-normal">SoulDragonVoid</h3>
                    <p>Enjoys building with nether blocks and doing keto things.</p>
                </div>
            </div><!-- /.row -->
        </div>


        <!-- START THE FEATURETTES -->

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading fw-normal lh-1">When and why the server was created</h2>
                <p class="lead">The No Go Outside server was started on May 11th, 2020. We were in high school at the time when covid struck. Our high school decided to end the year halfway through for the safety of their students, meaning that we had an extra long summer break. With all this extra free time, we started No Go Outside (the name was inspired by quarantine) and played on it an unhealthy amount throughout the longer summer break.</p>
            </div>
            <div class="col-md-5">
                <img src="${appURL}/images/smp/homepage/8.png" class="featurette-image img-fluid mx-auto object-fit-cover" width="500" height="500"/>
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7 order-md-2">
                <h2 class="featurette-heading fw-normal lh-1">Oh yeah, it’s that good. <span class="text-body-secondary">See for yourself.</span></h2>
                <p class="lead">Another featurette? Of course. More placeholder content here to give you an idea of how this layout would work with some actual real-world content in place.</p>
            </div>
            <div class="col-md-5 order-md-1">
                <svg class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 500x500" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="var(--bs-secondary-bg)"/><text x="50%" y="50%" fill="var(--bs-secondary-color)" dy=".3em">500x500</text></svg>
            </div>
        </div>

        <hr class="featurette-divider">

        <div class="row featurette">
            <div class="col-md-7">
                <h2 class="featurette-heading fw-normal lh-1">And lastly, this one. <span class="text-body-secondary">Checkmate.</span></h2>
                <p class="lead">And yes, this is the last block of representative placeholder content. Again, not really intended to be actually read, simply here to give you a better view of what this would look like with some actual content. Your content.</p>
            </div>
            <div class="col-md-5">
                <svg class="bd-placeholder-img bd-placeholder-img-lg featurette-image img-fluid mx-auto" width="500" height="500" xmlns="http://www.w3.org/2000/svg" role="img" aria-label="Placeholder: 500x500" preserveAspectRatio="xMidYMid slice" focusable="false"><title>Placeholder</title><rect width="100%" height="100%" fill="var(--bs-secondary-bg)"/><text x="50%" y="50%" fill="var(--bs-secondary-color)" dy=".3em">500x500</text></svg>
            </div>
        </div>

        <hr class="featurette-divider">

        <!-- /END THE FEATURETTES -->

    </div><!-- /.container -->
</main>
<%@ include file="/WEB-INF/smp/bottom.jsp"%>
