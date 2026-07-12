import "./Creators.css"
import Image from "next/image";
import creators from "../../../public/json/gg_team.json"

export default function Creators() {
    // TODO: Add a "GitHub" option for socials. Needs a new icon making, otherwise I'd just do it now :P
    // TODO: Add "Twitch" option.

    return <div className={"creators"}>
        {creators.people.map(creator => {
            return <div className={"creator"} key={creator.name}>
                <Image
                    src="/uiElements/ContainerDecoration.png"
                    width={128} height={16}
                    aria-hidden="true"
                    alt=""
                />
                <div className={"creator-img-container"}>
                    <Image
                        src={creator.image}
                        width={300}
                        height={300}
                        alt={creator.name}
                    />
                </div>
                <div className={"creator-info"}>
                    <hr/>
                    <h2>{creator.name}</h2>
                    <hr/>
                    {creator.quote ? <p className="quote">&#34;{creator.quote}&#34;</p> : null}

                    {creator.tags ? <div className="tags">
                        {creator.tags.map(tag => <span key={tag}>{tag}</span>)}
                    </div> : null}

                    {creator.socials ? <div className="socials">
                        {creator.socials.map(social =>
                            <a href={social.url} target="_blank" key={social.name}>
                                <Image
                                    src={"/icons/"+social.name+".png"}
                                    alt={social.name}
                                    width={32}
                                    height={32}
                                />
                            </a>
                        )}
                    </div> : null}
                </div>
                <Image
                    src="/uiElements/ContainerDecoration.png"
                    width={128} height={16}
                    aria-hidden="true"
                    alt=""
                />
        </div>

        })}
    </div>


}