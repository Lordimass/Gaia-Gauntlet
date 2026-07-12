import Image from "next/image";
import styles from "./page.module.css";
import Creators from "@/components/Creators/Creators";
import ImageMarquee from "@/components/ImageMarquee/ImageMarquee";

export default function Home() {
    return (
        <div className={styles.page}>
            <div className={styles.headerContainer}>
                <div className={styles.headerBg}/>
                <div className={styles.header}>
                    <Image
                        className={styles.gaia}
                        src="/branding/gaia.png"
                        alt="A golden Gaia statue"
                        width={350}
                        height={350}
                    />
                    <Image
                        className={styles.logo}
                        id={styles.ggLogo}
                        src="/branding/gg_text_glow.png"
                        alt="Gaia Gauntlet logo"
                        width={500}
                        height={300}
                        priority
                    />
                    <Image
                        className={styles.logo}
                        id={styles.ggCentredLogo}
                        src="/branding/gg_text_glow_centred.png"
                        alt="Gaia Gauntlet logo"
                        width={500}
                        height={300}
                        priority
                    />
                </div>
            </div>
            <div className={styles.mainHeader}>
                <Image
                    src="/uiElements/ContainerDecoration.png"
                    width={200} height={16}
                    aria-hidden="true"
                    alt=""
                />
                <div>
                    <h1>One event.<br/> Countless creators.<br/> One champion.</h1>
                    <Image
                        src="/branding/5_point_tree.svg"
                        width={100} height={100}
                        alt={"An icon of a five point tree with nodes at the end of each branch."}
                    />
                </div>

                <Image
                    src="/uiElements/ContainerDecoration.png"
                    width={200} height={16}
                    aria-hidden="true"
                    alt=""
                />
            </div>


            <ImageMarquee images={["/twitterPosts/post1.png", "/twitterPosts/post2.png"]} />
            <main className={styles.main}>
                <div className={styles.intro}>
                    <h2>The GG Team</h2>
                    <Creators />
                    <blockquote>
                        &#34;I&#39;m just one part of the team of people who work on making Gaia Gauntlet a reality. We all do
                        this out of passion and love for the community, and none of us are paid, so we really appreciate
                        your support. Thank you to everyone who contributed to making this project a reality, you're all
                        amazing!&#34;
                    </blockquote>
                    <p>- Lordimass</p>
                </div>
            </main>
        </div>
    );
}
