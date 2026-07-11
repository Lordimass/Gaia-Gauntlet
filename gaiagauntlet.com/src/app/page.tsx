import Image from "next/image";
import styles from "./page.module.css";
import Creators from "@/components/Creators/Creators";

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
                </div>
            </div>
            <h1 className={styles.mainHeader}>One event.<br/> Countless creators.<br/> One champion.</h1>

            <main className={styles.main}>
                <div className={styles.intro}>
                    <h2>The GG Team</h2>
                    <Creators />
                    <blockquote>
                        "I'm just one part of the team of people who work on making Gaia Gauntlet a reality. We all do
                        this out of passion and love for the community, and none of us are paid, so we really appreciate
                        your support. Thank you to everyone who contributed to making this project a reality, you're all
                        amazing!"
                    </blockquote>
                    <p>- Lordimass</p>
                </div>
            </main>
        </div>
    );
}
